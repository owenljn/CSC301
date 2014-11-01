package com.utoronto.timemng.descriptor.type;

/**
 * A class storing a type for each month.
 */
public enum WhichMonth {
    JANUARY("January"),
    FEBRUARY("February"),
    MARCH("March"),
    APRIL("April"),
    MAY("May"),
    JUNE("June"),
    JULY("July"),
    AUGUST("August"),
    SEPTEMBER("September"),
    OCTOBER("October"),
    NOVEMBER("November"),
    DECEMBER("December");

    private final String month;

    /**
     * Default constructor for this class.
     * @param month the month name.
     */
    WhichMonth(final String month) {
        this.month = month;
    }

    /**
     * Gets the string value for the month type.
     * @return  string value for the month type.
     */
    public String getValue() {
        return this.month;
    }

    /**
     * Gets the month type given its name.
     * @param month string name of the month.
     * @return      the type for the month, or null if month name invalid.
     */
    public static WhichMonth lookup(final String month) {
        for (final WhichMonth m : values()) {
            if (month.equals(m.getValue())) {
                return m;
            }
        }
        return null;
    }
}
