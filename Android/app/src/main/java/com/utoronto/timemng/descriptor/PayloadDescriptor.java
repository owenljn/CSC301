package com.utoronto.timemng.descriptor;

/**
 * Contains the type of action (e.g. create, delete, update event), and the month.
 */
public class PayloadDescriptor {
    private final int payloadAction;
    private final Year year;

    /**
     * The default constructor.
     * @param payloadAction the integer value for payload action.
     * @param yr            the Year object.
     */
    public PayloadDescriptor(final int payloadAction, final Year yr) {
        if (null != yr) {
            this.payloadAction = payloadAction;
            this.year = yr;
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
    public Year getYear() {
        return this.year;
    }
}
