package org.synopia.vertxfx.contact;

import org.junit.Assert;
import org.junit.Test;
import org.synopia.vertxfx.rpc.RPC;
import org.synopia.vertxfx.rpc.ServerRPC;
import org.synopia.vertxfx.session.SessionService;
import org.synopia.vertxfx.session.SessionVerticle;
import org.vertx.testtools.TestVerticle;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.vertx.testtools.VertxAssert.testComplete;

/**
 * @author synopia
 */
public class ContactVerticleTest extends TestVerticle {

    @Test
    public void testIt() throws InterruptedException {
        container.deployVerticle(SessionVerticle.class.getName());
        container.deployVerticle(ContactVerticle.class.getName());
        Thread.sleep(100);

        RPC rpc = new ServerRPC(vertx.eventBus());
        rpc.get(SessionService.class).createSession("foo", "foo").setHandler(result -> {
            ContactService service = rpc.get(ContactService.class);
            service.listAll(result.result()).setHandler(e -> {
                Assert.assertEquals(Arrays.asList("foo", "bar"), e.result().stream().map(Contact::getName).collect(Collectors.toList()));
                testComplete();
            });
        });
    }

    @Test
    public void testCrud() throws InterruptedException {
        container.deployVerticle(ContactVerticle.class.getName());
        Thread.sleep(100);
        testComplete();
//        ContactService.Async service = RPC.getService(vertx.eventBus(), ContactService.class.getName(), ContactService.Async.class);
//        Contact contact = new Contact("xx", "xx", "xx", "xx");
//        service.create(contact, event -> service.read(event, event1 -> {
//            Assert.assertEquals(contact, event1);
//            testComplete();
//        }));
    }
}
