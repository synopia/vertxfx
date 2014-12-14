package org.synopia.vertxfx.rpc;

import org.vertx.java.core.Future;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.impl.DefaultFutureResult;

/**
 * Created by synopia on 14.12.2014.
 */
public class ServerRPC extends RPC {
    public ServerRPC(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    public Future createFuture() {
        return new DefaultFutureResult<>();
    }
}
