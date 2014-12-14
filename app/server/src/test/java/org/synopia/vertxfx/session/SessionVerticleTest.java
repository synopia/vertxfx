package org.synopia.vertxfx.session;

import org.junit.Test;
import org.synopia.vertxfx.rpc.RPC;
import org.synopia.vertxfx.rpc.ServerRPC;
import org.vertx.testtools.TestVerticle;

import static org.junit.Assert.assertTrue;
import static org.vertx.testtools.VertxAssert.testComplete;

/**
 * @author synopia
 */
public class SessionVerticleTest extends TestVerticle {
    @Test
    public void testSuccess() throws InterruptedException {
        container.deployVerticle(SessionVerticle.class.getName());
        Thread.sleep(100);
        RPC rpc = new ServerRPC(vertx.eventBus());
        SessionService service = rpc.get(SessionService.class);
        service.createSession("foo", "foo").setHandler(result -> {
            assertTrue(result.succeeded());
            assertTrue(!SessionKey.INVALID.equals(result.result()));

            service.checkSession(result.result()).setHandler(r -> {
                assertTrue(r.succeeded());
                assertTrue(r.result());
                testComplete();
            });
        });
    }

    @Test
    public void testFailure() throws InterruptedException {
        container.deployVerticle(SessionVerticle.class.getName());
        Thread.sleep(100);
        RPC rpc = new ServerRPC(vertx.eventBus());
        SessionService service = rpc.get(SessionService.class);
        service.createSession("foo", "bar").setHandler(result -> {
            assertTrue(result.succeeded());
            assertTrue(SessionKey.INVALID.equals(result.result()));
            testComplete();
        });
    }
}
