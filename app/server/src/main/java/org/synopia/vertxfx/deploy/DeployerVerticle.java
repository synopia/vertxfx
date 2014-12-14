package org.synopia.vertxfx.deploy;

import org.synopia.vertxfx.contact.ContactVerticle;
import org.synopia.vertxfx.rpc.RPCService;
import org.synopia.vertxfx.session.SessionVerticle;
import org.vertx.java.core.Future;
import org.vertx.java.core.impl.DefaultFutureResult;

/**
 * @author synopia
 */
public class DeployerVerticle extends RPCService<DeployService> implements DeployService {
    public DeployerVerticle() {
        super(DeployService.class);
    }

    @Override
    public void start() {
        container.deployVerticle(SessionVerticle.class.getName());
        container.deployVerticle(ContactVerticle.class.getName());
        super.start();
    }

    @Override
    public Future<Long> ping(long timestamp) {
        return new DefaultFutureResult<>(timestamp);
    }
}
