package org.synopia.vertxfx.deploy;

import org.vertx.java.core.Future;

/**
 * Created by synopia on 14.12.2014.
 */
public interface DeployService {
    Future<Long> ping(long timestamp);
}
