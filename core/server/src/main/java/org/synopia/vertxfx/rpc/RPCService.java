package org.synopia.vertxfx.rpc;

import com.google.common.collect.Maps;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Future;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author synopia
 */
public abstract class RPCService<T> extends Verticle {
    private ServerRPC serverRPC;
    private final Class<T> service;
    private final Map<String, Method> methods = Maps.newHashMap();

    public RPCService(Class<T> service) {
        this.service = service;

        scanMethods();
    }

    protected <U> U get(Class<U> service) {
        return serverRPC.get(service);
    }

    @Override
    public void start() {
        serverRPC = new ServerRPC(vertx.eventBus());
        container.logger().info("Starting " + getClass().getName());
        vertx.eventBus().registerHandler(getAddress(), new Handler<Message<Buffer>>() {
            @Override
            public void handle(Message<Buffer> message) {
                invokeAndHandle(FSTUtil.read(message.body()), event -> message.reply(FSTUtil.write(event.result())));
            }
        });
    }

    public void invokeAndHandle(RPCInvocation invocation, Handler<AsyncResult> replyHandler) {
        Method method = methods.get(invocation.method);
        if (method != null) {
            try {
                Future result = (Future) method.invoke(RPCService.this, invocation.parameters);
                result.setHandler(replyHandler);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getAddress() {
        return service.getName();
    }

    private void scanMethods() {
        for (Method method : service.getMethods()) {
            methods.put(method.getName(), method);
        }
    }
}
