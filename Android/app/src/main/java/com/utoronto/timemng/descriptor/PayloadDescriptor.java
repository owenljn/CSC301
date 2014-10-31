package com.utoronto.timemng.descriptor;

/**
 * Contains the type of action (e.g. create, delete, update event), and the month.
 */
public class PayloadDescriptor {
    private final int payloadAction;
    private final Month month;

    /**
     * The default constructor.
     * @param payloadAction the integer value for payload action.
     * @param month         the Month object.
     */
    public PayloadDescriptor(final int payloadAction, final Month month) {
        if (null != month) {
            this.payloadAction = payloadAction;
            this.month = month;
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
     * @return  the Month object.
     */
    public Month getMonth() {
        return this.month;
    }
}
