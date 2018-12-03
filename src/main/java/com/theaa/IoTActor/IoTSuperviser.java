package com.theaa.IoTActor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by: fuzayl
 * Date: 03 Dec, 2018
 * Company: Finnet Limited
 */
public class IoTSuperviser extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    static Props props() {
        return Props.create(IoTSuperviser.class, IoTSuperviser::new);
    }

    @Override
    public void preStart() throws Exception {
        log.info("IoT Application started");
    }

    @Override
    public void postStop() throws Exception {
        log.info("IoT Application stopped");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .build();
    }


}
