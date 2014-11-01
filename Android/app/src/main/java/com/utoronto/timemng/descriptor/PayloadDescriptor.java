package com.utoronto.timemng.descriptor;

import java.util.Collection;

/**
 * Contains the type of action (e.g. create, delete, update event), and the month.
 */
public class PayloadDescriptor {
    private final int payloadAction;
    private final Collection<Event> events;

    /**
     * The default constructor.
     * @param payloadAction the integer value for payload action.
     * @param events        collection of events.
     */
    public PayloadDescriptor(final int payloadAction, final Collection<Event> events) {
        if (null != events && !events.isEmpty()) {
            this.payloadAction = payloadAction;
            this.events = events;
        } else {
            throw new IllegalArgumentException("Cannot construct PayloadDescriptor with null" +
                    "month");
        }
    }

    /**
     * Gets the payload action.
     * @return  the integer value for payload action.
     */
    public int getPayloadAction() {
        return this.payloadAction;
    }

    /**
     * Gets the month for the action.
     * @return  the Year object.
     */
    public Collection<Event> getYear() {
        return this.events;
    }
}
