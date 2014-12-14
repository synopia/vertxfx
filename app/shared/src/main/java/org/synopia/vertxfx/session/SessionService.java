package org.synopia.vertxfx.session;

import org.vertx.java.core.Future;

/**
 * @author synopia
 */
public interface SessionService {
    Future<SessionKey> createSession(String login, String password);

    Future<Boolean> checkSession(SessionKey sessionId);
}
