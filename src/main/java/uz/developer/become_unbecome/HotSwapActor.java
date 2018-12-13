package uz.developer.become_unbecome;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by: fuzayl
 * Date: 13 Dec, 2018
 * Company: Finnet Limited
 */
public class HotSwapActor extends AbstractActor {
    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private AbstractActor.Receive angry;
    private AbstractActor.Receive happy;

    static Props props() {
        return Props.create(HotSwapActor.class, HotSwapActor::new);
    }

    public HotSwapActor() {
        angry = receiveBuilder()
                .matchEquals("tell", s -> {
                    getSender().tell("I am already angry ?", getSelf());
                    log.info("I am already angry");
                })
                .matchEquals("change", s -> {
                    getContext().become(happy);
                    log.info("Changed");
                })
                .build();

        happy = receiveBuilder()
                .matchEquals("tell", s -> {
                    getSender().tell("I am already happy :-", getSelf());
                    log.info("I am already happy");
                })
                .matchEquals("change", s -> {
                    getContext().become(angry);
                    log.info("Changed");
                })
                .build();

    }

    @Override
    public void preStart() throws Exception {
        log.info("HotSwapActor started");
    }

    @Override
    public void postStop() throws Exception {
        log.info("HotSwapActor stopped");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("be angry", s -> {
                    getContext().become(angry);
                })
                .matchEquals("be happy", s-> {
                    getContext().become(happy);
                })
                .build();
    }
}
