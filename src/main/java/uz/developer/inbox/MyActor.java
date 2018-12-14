package uz.developer.inbox;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by: fuzayl
 * Date: 13 Dec, 2018
 * Company: Finnet Limited
 */
public class MyActor extends AbstractActor {

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    static Props props() {
        return Props.create(MyActor.class, MyActor::new);
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("messageFromInbox", msg -> {
                    getSender().tell("Message to Inbox", getSelf());
                })
                .build();
    }
}
