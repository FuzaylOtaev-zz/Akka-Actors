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
public class Swapper extends AbstractActor {
    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    static Props props() {
        return Props.create(Swapper.class, Swapper::new);
    }

    @Override
    public void preStart() throws Exception {
        log.info("Swapper started");
    }

    @Override
    public void postStop() throws Exception {
        log.info("Swapper stopped");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("msg", msg -> {
                    log.info("Hi");
                    getContext().become(receiveBuilder()
                            .matchEquals("msg", m -> {
                                log.info("Ho");
                                getContext().unbecome();
                            })
                            .build(), false);
                })
                .build();
    }
}
