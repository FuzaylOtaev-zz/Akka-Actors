package uz.developer.inbox;

import akka.actor.*;
import akka.testkit.javadsl.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Duration;

/**
 * Created by: fuzayl
 * Date: 13 Dec, 2018
 * Company: Finnet Limited
 */
public class InboxTest {

    static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void inboxRecieveTerminated() {
        ActorRef myActor = system.actorOf(MyActor.props());

        final Inbox inbox = Inbox.create(system);
        inbox.watch(myActor);
        myActor.tell(PoisonPill.getInstance(), ActorRef.noSender());
        try {
            assert inbox.receive(Duration.ofSeconds(1)) instanceof Terminated;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void inboxSendMessage() {
        ActorRef myActor = system.actorOf(MyActor.props());

        final Inbox inbox = Inbox.create(system);
        inbox.send(myActor, "messageFromInbox");
        try {
            assert inbox.receive(Duration.ofSeconds(1)) instanceof String;
        } catch (Exception e) {

        }
    }
}
