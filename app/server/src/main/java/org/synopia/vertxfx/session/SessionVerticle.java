package org.synopia.vertxfx.session;

import com.google.common.collect.Lists;
import org.synopia.vertxfx.rpc.RPCService;
import org.vertx.java.core.Future;
import org.vertx.java.core.impl.DefaultFutureResult;

import java.util.List;
import java.util.UUID;

/**
 * @author synopia
 */
public class SessionVerticle extends RPCService<SessionService> implements SessionService {
    private List<SessionKey> sessions = Lists.newArrayList();

    public SessionVerticle() {
        super(SessionService.class);
    }

    @Override
    public Future<SessionKey> createSession(String login, String password) {
        if (login != null && login.equals(password)) {
            SessionKey sessionKey = new SessionKey(UUID.randomUUID().toString());
            sessions.add(sessionKey);
            return new DefaultFutureResult<>(sessionKey);
        }
        return new DefaultFutureResult<>(SessionKey.INVALID);
    }

    @Override
    public Future<Boolean> checkSession(SessionKey sessionId) {
        return new DefaultFutureResult<>(sessions.contains(sessionId));
    }
}
