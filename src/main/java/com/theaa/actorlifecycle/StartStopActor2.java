package com.theaa.actorlifecycle;

import akka.actor.AbstractActor;
import akka.actor.Props;

/**
 * Created by: fuzayl
 * Date: 30 Nov, 2018
 * Company: Finnet Limited
 */
public class StartStopActor2 extends AbstractActor {

    static Props props() {
        return Props.create(StartStopActor2.class, StartStopActor2::new);
    }

    @Override
    public void preStart() throws Exception {
        System.out.println("Second actor started");
    }

    @Override
    public void postStop() throws Exception {
        System.out.println("Second actor stopped");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .build();
    }
}
