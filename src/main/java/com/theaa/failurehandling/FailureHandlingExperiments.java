package com.theaa.failurehandling;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.io.IOException;

/**
 * Created by: fuzayl
 * Date: 03 Dec, 2018
 * Company: Finnet Limited
 */
public class FailureHandlingExperiments {
    public static void main(String[] args) throws IOException {
        ActorSystem system = ActorSystem.create("system");

        ActorRef supervisingActor = system.actorOf(SupervisingActor.props(), "supervising-actor");
        supervisingActor.tell("failChild", ActorRef.noSender());

        try {
            System.in.read();
        } finally {
            system.terminate();
        }
    }
}
