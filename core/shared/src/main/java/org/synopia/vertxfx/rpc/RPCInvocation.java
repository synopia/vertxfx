package org.synopia.vertxfx.rpc;

import java.io.Serializable;

/**
 * @author synopia
 */
public class RPCInvocation implements Serializable {
    public String method;
    public Object[] parameters;
}
