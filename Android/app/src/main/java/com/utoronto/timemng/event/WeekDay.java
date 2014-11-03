package com.utoronto.timemng.event;

/**
 * 31/10/2014 21:37.
 */
public enum WeekDay {
    SUNDAY(1, "Sunday"),
    MONDAY(2, "Monday"),
    TUESDAY(3, "Tuesday"),
    WEDNESDAY(4, "Wednesday"),
    THURSDAY(5, "Thursday"),
    FRIDAY(6, "Friday"),
    SATURDAY(7, "Saturday");

    private final int dayNum;
    private final String dayStr;

    /**
     * The default constructor for the class.
     * @param dayNum    day number.
     * @param dayStr    day of the week.
     */
    WeekDay(final int dayNum, final String dayStr) {
        this.dayNum = dayNum;
        this.dayStr = dayStr;
    }

    /**
     * Gets the day number.
     * @return  the day number.
     */
    public int getDayNum() {
        return this.dayNum;
    }

    /**
     * Gets the day of the week.
     * @return  day of the week string.
     */
    public String getDayStr() {
        return this.dayStr;
    }

    /**
     * Gets the type for day of the week, given day number.
     * @param numDay   day number.
     * @return         type for day of the week.
     */
    public WeekDay lookup(final int numDay) {
        for (final WeekDay weekDay : values()) {
            if (numDay == getDayNum()) {
                return weekDay;
            }
        }
        return null;
    }

    /**
     * Gets the type for the day of the week, given day of week string.
     * @param strDay    day of the week string.
     * @return          type for day of the week.
     */
    public static WeekDay lookup(final String strDay) {
        for (final WeekDay weekDay : values()) {
            if (strDay.equals(weekDay.getDayStr())) {
                return weekDay;
            }
        }
        return null;
    }
}

