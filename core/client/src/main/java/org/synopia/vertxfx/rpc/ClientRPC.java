package org.synopia.vertxfx.rpc;

import org.vertx.java.core.Future;
import org.vertx.java.core.eventbus.EventBus;

/**
 * Created by synopia on 14.12.2014.
 */
public class ClientRPC extends RPC {

    public ClientRPC(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    public Future createFuture() {
        return new ClientFutureResult<>();
    }
}
