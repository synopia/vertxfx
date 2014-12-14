package org.synopia.vertxfx.contact;

import com.google.common.collect.Lists;
import org.synopia.vertxfx.persist.PK;
import org.synopia.vertxfx.rpc.RPCInvocation;
import org.synopia.vertxfx.rpc.RPCService;
import org.synopia.vertxfx.session.SessionKey;
import org.synopia.vertxfx.session.SessionService;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Future;
import org.vertx.java.core.Handler;
import org.vertx.java.core.impl.DefaultFutureResult;

import java.util.List;

/**
 * @author synopia
 */
public class ContactVerticle extends RPCService<ContactService> implements ContactService {
    private List<Contact> db = Lists.newArrayList();

    public ContactVerticle() {
        super(ContactService.class);

        db.add(new Contact("foo", "1", "2", "3"));
        db.add(new Contact("bar", "a", "b", "c"));

        for (int i = 0; i < db.size(); i++) {
            Contact contact = db.get(i);
            contact.getId().set((long) i);
        }
    }

    @Override
    public void invokeAndHandle(RPCInvocation invocation, Handler<AsyncResult> replyHandler) {
        for (Object parameter : invocation.parameters) {
            if (parameter != null && parameter.getClass() == SessionKey.class) {
                get(SessionService.class).checkSession((SessionKey) parameter).setHandler(result -> super.invokeAndHandle(invocation, replyHandler));
            }
        }
    }

    @Override
    public Future<List<Contact>> listAll(SessionKey sessionKey) {
        return new DefaultFutureResult<>(db);
    }

    @Override
    public Future<PK<Contact>> create(SessionKey sessionKey, Contact contact) {
        contact.getId().set((long) db.size());
        db.add(contact);
        return new DefaultFutureResult<>(contact.getId());
    }

    @Override
    public Future<Contact> read(SessionKey sessionKey, PK<Contact> id) {
        return new DefaultFutureResult<>(db.get(id.get().intValue()));
    }

    @Override
    public Future<Boolean> update(SessionKey sessionKey, Contact contact) {
        db.set(contact.getId().get().intValue(), contact);
        return new DefaultFutureResult<>(true);
    }

    @Override
    public Future<Boolean> delete(SessionKey sessionKey, PK<Contact> id) {
        db.remove(id.get().intValue());
        return new DefaultFutureResult<>(true);
    }
}
