package com.theaa.IoTActor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.io.IOException;

/**
 * Created by: fuzayl
 * Date: 03 Dec, 2018
 * Company: Finnet Limited
 */
public class IoTMain {
    public static void main(String[] args) throws IOException {
        ActorSystem system = ActorSystem.create("iot-system");

        try {
            ActorRef supervisor = system.actorOf(IoTSuperviser.props(), "iot-superviser");
            System.in.read();
        } finally {
            system.terminate();
        }
    }
}
