package com.theaa.actorlifecycle;

import akka.actor.AbstractActor;
import akka.actor.Props;

/**
 * Created by: fuzayl
 * Date: 30 Nov, 2018
 * Company: Finnet Limited
 */
public class StartStopActor1 extends AbstractActor {

    static Props props() {
        return Props.create(StartStopActor1.class, StartStopActor1::new);
    }

    @Override
    public void preStart() throws Exception {
        System.out.println("First actor started");
        getContext().actorOf(StartStopActor2.props(), "second-actor");
    }

    @Override
    public void postStop() throws Exception {
        System.out.println("First actor stopped");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("stop", s -> {
                    getContext().stop(getSelf());
                })
                .build();
    }
}
