package org.synopia.vertxfx.session;

import java.io.Serializable;

/**
 * Created by synopia on 14.12.2014.
 */
public class SessionKey implements Serializable {
    public static final SessionKey INVALID = new SessionKey("invalid");
    public final String key;

    public SessionKey() {
        this(INVALID.key);
    }

    public SessionKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessionKey that = (SessionKey) o;

        return key.equals(that.key);

    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
