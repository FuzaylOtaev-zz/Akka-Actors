package uz.developer.become_unbecome;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * Created by: fuzayl
 * Date: 13 Dec, 2018
 * Company: Finnet Limited
 */
public class BecomeUnbecomeExperiment {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("system");
        test2(system);

    }

    private static void test1(ActorSystem system) {
        ActorRef hotSwapActor = system.actorOf(HotSwapActor.props());

        hotSwapActor.tell("be angry", ActorRef.noSender());
        hotSwapActor.tell("tell", ActorRef.noSender());
        hotSwapActor.tell("change", ActorRef.noSender());

        hotSwapActor.tell("tell", ActorRef.noSender());
        hotSwapActor.tell("change", ActorRef.noSender());
    }

    private static void test2(ActorSystem system) {
        ActorRef swapper = system.actorOf(Swapper.props());
        swapper.tell("msg", ActorRef.noSender());
        swapper.tell("msg", ActorRef.noSender());
        swapper.tell("msg", ActorRef.noSender());
        swapper.tell("msg", ActorRef.noSender());
    }
}
