package org.synopia.vertxfx.contact;

import org.synopia.vertxfx.persist.PK;
import org.synopia.vertxfx.session.SessionKey;
import org.vertx.java.core.Future;

import java.util.List;

/**
 * @author synopia
 */
public interface ContactService {
    Future<List<Contact>> listAll(SessionKey sessionKey);

    Future<PK<Contact>> create(SessionKey sessionKey, Contact contact);

    Future<Contact> read(SessionKey sessionKey, PK<Contact> id);

    Future<Boolean> update(SessionKey sessionKey, Contact contact);

    Future<Boolean> delete(SessionKey sessionKey, PK<Contact> id);
}
