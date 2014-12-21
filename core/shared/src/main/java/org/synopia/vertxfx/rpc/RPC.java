package org.synopia.vertxfx.rpc;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Future;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author synopia
 */
public abstract class RPC {

    private final EventBus eventBus;

    public RPC(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @SuppressWarnings("unchecked")
    public <U> U get(Class<U> service) {
        return (U) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{service}, (proxy, method, args) -> RPC.this.invoke(service, method, args));
    }

    @SuppressWarnings("unchecked")
    public Object invoke(Class<?> service, Method method, Object[] args) throws Throwable {
        RPCInvocation invocation = new RPCInvocation();
        invocation.method = method.getName();
        invocation.parameters = args;
        Buffer buffer = FSTUtil.write(invocation);
        Future future = createFuture();
        eventBus.sendWithTimeout(service.getName(), buffer, 1000, new Handler<AsyncResult<Message<Buffer>>>() {
            @Override
            public void handle(AsyncResult<Message<Buffer>> event) {
                if (event.succeeded()) {
                    future.setResult(FSTUtil.read(event.result().body()));
                } else {
                    future.setFailure(event.cause());
                }
            }
        });
        return future;
    }

    public abstract Future createFuture();
}
