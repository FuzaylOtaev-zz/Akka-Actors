package com.theaa.failurehandling;

import akka.actor.AbstractActor;
import akka.actor.Props;

/**
 * Created by: fuzayl
 * Date: 03 Dec, 2018
 * Company: Finnet Limited
 */
public class SupervisedActor extends AbstractActor {

    static Props props() {
        return Props.create(SupervisedActor.class, SupervisedActor::new);
    }

    @Override
    public void preStart() throws Exception {
        System.out.println("Supervised actor started");
    }

    @Override
    public void postStop() throws Exception {
        System.out.println("Supervised actor stopped");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("fail", f -> {
                    System.out.println("Supervised actor fails now");
                    throw new Exception("I failed");
                })
                .build();
    }
}
