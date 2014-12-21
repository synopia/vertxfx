package org.synopia.vertxfx.formular;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Created by synopia on 21.12.2014.
 */
public class FormEvent extends Event {
    public static final EventType<FormEvent> ANY = new EventType<>(Event.ANY, "FORM");
    public static final EventType<FormEvent> FORM_SUBMIT = new EventType<>(FormEvent.ANY, "FORM_SUBMIT");
    public static final EventType<FormEvent> FORM_FINISHED = new EventType<>(FormEvent.ANY, "FORM_FINISHED");
    public static final FormEvent SUBMIT = new FormEvent(FORM_SUBMIT);

    private final int id;

    public FormEvent(EventType<? extends Event> eventType) {
        this(eventType, -1);
    }

    public FormEvent(EventType<? extends Event> eventType, int id) {
        super(eventType);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
