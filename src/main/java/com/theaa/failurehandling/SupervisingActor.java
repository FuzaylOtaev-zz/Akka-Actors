package com.theaa.failurehandling;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

/**
 * Created by: fuzayl
 * Date: 03 Dec, 2018
 * Company: Finnet Limited
 */
public class SupervisingActor extends AbstractActor {

    private ActorRef child = null;

    static Props props() {
        return Props.create(SupervisingActor.class, SupervisingActor::new);
    }

    @Override
    public void preStart() throws Exception {
        System.out.println("Supervising actor started");
        child = getContext().actorOf(SupervisedActor.props(), "supervised-actor");
    }

    @Override
    public void postStop() throws Exception {
        System.out.println("Supervising actor stopped");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("failChild", f -> {
                    child.tell("fail", getSelf());
                })
                .build();
    }
}
