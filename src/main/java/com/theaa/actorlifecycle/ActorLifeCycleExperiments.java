package com.theaa.actorlifecycle;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.io.IOException;

/**
 * Created by: fuzayl
 * Date: 30 Nov, 2018
 * Company: Finnet Limited
 */
public class ActorLifeCycleExperiments {
    public static void main(String[] args) throws IOException {
        ActorSystem system = ActorSystem.create("testSystem");

        ActorRef firstRef = system.actorOf(StartStopActor1.props(), "first-actor");
        firstRef.tell("stop", ActorRef.noSender());

        System.out.println(">>> Press ENTER to exit <<<");
        try {
            System.in.read();
        } finally {
            system.terminate();
        }

    }
}
