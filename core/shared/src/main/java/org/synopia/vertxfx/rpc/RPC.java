package org.synopia.vertxfx.rpc;

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
        eventBus.send(service.getName(), buffer, new Handler<Message<Buffer>>() {
            @Override
            public void handle(Message<Buffer> event) {
                future.setResult(FSTUtil.read(event.body()));
            }
        });
        return future;
    }

    public abstract Future createFuture();
}
