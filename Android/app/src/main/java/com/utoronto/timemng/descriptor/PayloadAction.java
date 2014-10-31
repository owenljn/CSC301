package com.utoronto.timemng.descriptor;

/**
 * Defines the possible actions that can be implemented on the payload.
 */
public enum PayloadAction {
    UNKNOWN(0),
    CREATE_EVENT(1),
    UPDATE_EVENT(2),
    DELETE_EVENT(3);

    private final int value;

    /**
     * Default constructor.
     * @param actionValue   the numeric value for the action type.
     */
    PayloadAction(int actionValue) {
        this.value = actionValue;
    }

    /**
     * Returns the integer value of the payload action.
     * @return  integer value of payload action.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Determines the type of action based on the integer value.
     * @param actionValue   the integer value for the action.
     * @return              the type of action.
     */
    public PayloadAction lookup(int actionValue) {
        for (PayloadAction p : values()) {
            if (p.getValue() == actionValue) {
                return p;
            }
        }
        return UNKNOWN;
    }
}
