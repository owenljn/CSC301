package com.utoronto.timemng.deserializer;

import com.utoronto.timemng.descriptor.Event;

import java.util.Collection;
import java.util.List;

/**
 * Contains the type of action (e.g. create, delete, update event), and the month.
 */
public class PayloadContainer {
    private final Collection<Event> events;

    /**
     * The default constructor.
     * @param events        list of events.
     */
    public PayloadContainer(final List<Event> events) {
        if (null != events) {
            this.events = events;
        } else {
            throw new IllegalArgumentException("Cannot construct PayloadDescriptor" +
                    " with events set to null");
        }
    }

    /**
     * Gets the month for the action.
     * @return  the Year object.
     */
    public Collection<Event> getEvents() {
        return this.events;
    }
}
