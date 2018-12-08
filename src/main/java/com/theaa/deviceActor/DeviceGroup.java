package com.theaa.deviceActor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by: fuzayl
 * Date: 03 Dec, 2018
 * Company: Finnet Limited
 */
public class DeviceGroup extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    static Props props(String groupId) {
        return Props.create(DeviceGroup.class, () -> new DeviceGroup(groupId));
    }

    final String groupId;

    public DeviceGroup(String groupId) {
        this.groupId = groupId;
    }

    public static final class RequestDeviceList {
        final long requestId;

        public RequestDeviceList(long requestId) {
            this.requestId = requestId;
        }
    }

    public static final class ReplyDeviceList {
        final long requestId;
        final Set<String> ids;

        public ReplyDeviceList(long requestId, Set<String> ids) {
            this.requestId = requestId;
            this.ids = ids;
        }
    }

    final Map<String, ActorRef> deviceIdToActor = new HashMap<>();
    final Map<ActorRef, String> actorToDeviceId = new HashMap<>();

    @Override
    public void preStart() throws Exception {
        log.info("DeviceGroup {} started", groupId);
    }

    @Override
    public void postStop() throws Exception {
        log.info("DeviceGroup {} stopped", groupId);
    }

    private void onTrackDevice(DeviceManager.RequestTrackDevice trackMsg) {
        if (this.groupId.equals(trackMsg.groupId)) {
            ActorRef deviceActor = deviceIdToActor.get(trackMsg.deviceId);
            if (deviceActor != null) {
                deviceActor.forward(trackMsg, getContext());
            } else {
                log.info("Creating device actor for {}", trackMsg.deviceId);
                deviceActor = getContext().actorOf(Device.props(groupId, trackMsg.deviceId), "device-" + trackMsg.deviceId);
                getContext().watch(deviceActor);
                deviceIdToActor.put(trackMsg.deviceId, deviceActor);
                actorToDeviceId.put(deviceActor, trackMsg.deviceId);
                deviceActor.forward(trackMsg, getContext());
            }
        } else {
            log.warning(
                    "Ignoring TrackDevice request for {}. This actor is responsible for {}.",
                    groupId, this.groupId
            );
        }
    }

    private void onTerminated(Terminated terminated) {
        ActorRef deviceActor = terminated.getActor();
        String deviceId = actorToDeviceId.get(deviceActor);
        log.info("================Device Actor has been terminated==============");
        actorToDeviceId.remove(deviceActor);
        deviceIdToActor.remove(deviceId);
    }

    private void onDeviceList(RequestDeviceList requestDeviceList) {
        getSender().tell(new ReplyDeviceList(requestDeviceList.requestId, deviceIdToActor.keySet()), getSelf());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(RequestDeviceList.class, this::onDeviceList)
                .match(DeviceManager.RequestTrackDevice.class, this::onTrackDevice)
                .match(Terminated.class, this::onTerminated)
                .build();
    }
}
