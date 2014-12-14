package org.synopia.vertxfx.rpc;

import javafx.application.Platform;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Future;
import org.vertx.java.core.Handler;
import org.vertx.java.core.impl.DefaultFutureResult;

/**
 * Created by synopia on 14.12.2014.
 */
public class ClientFutureResult<T> implements Future<T> {
    private Future<T> delegate;

    public ClientFutureResult() {
        delegate = new DefaultFutureResult<>();
    }

    public ClientFutureResult(Throwable t) {
        delegate = new DefaultFutureResult<>(t);
    }

    public ClientFutureResult(T result) {
        delegate = new DefaultFutureResult<>(result);
    }

    @Override
    public boolean complete() {
        return delegate.complete();
    }

    @Override
    public T result() {
        return delegate.result();
    }

    @Override
    public Throwable cause() {
        return delegate.cause();
    }

    @Override
    public boolean succeeded() {
        return delegate.succeeded();
    }

    @Override
    public boolean failed() {
        return delegate.failed();
    }

    @Override
    public DefaultFutureResult<T> setHandler(Handler<AsyncResult<T>> handler) {
        Platform.runLater(() -> delegate.setHandler(handler));
        return null;
    }

    @Override
    public DefaultFutureResult<T> setResult(T result) {
        Platform.runLater(() -> delegate.setResult(result));
        return null;
    }

    @Override
    public DefaultFutureResult<T> setFailure(Throwable throwable) {
        Platform.runLater(() -> delegate.setFailure(throwable));
        return null;
    }
}
