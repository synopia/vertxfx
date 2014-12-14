package org.synopia.vertxfx.persist;

import java.io.Serializable;

/**
 * @author synopia
 */
public class PK<T> implements Serializable {
    private Long id;

    public Long get() {
        return id;
    }

    public void set(Long id) {
        this.id = id;
    }
}
