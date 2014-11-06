package com.utoronto.timemng.calendar;

import java.util.Calendar;

/**
 * A single day cell. Used in GridView for constructing the calendar.
 */
public class DayCell {

    /**
     * Tag for logging purposes.
     */
    private static final String TAG = "c2dm daycell";

    /**
     * Calendar with a particular day of the month.
     */
    private final Calendar dayCal;

    /**
     * Day of the month as integer.
     */
    private final int dayOfMonth;

    /**
     * Carries information for the calendar day.
     * @param dayCal        the date for this day.
     * @param dayOfMonth    the day of the month in integer format.
     */
    public DayCell(final Calendar dayCal, final int dayOfMonth) {
        super();
        this.dayCal = dayCal;
        this.dayOfMonth = dayOfMonth;
    }

    /**
     * Gets Calendar object with relevant date.
     * @return  calendar object for this day.
     */
    public Calendar getDayCal() {
        return this.dayCal;
    }

    /**
     * Gets the integer representation of this day of month.
     * @return  number representation of day of month for this DayCell object.
     */
    public int getDayOfMonth() {
        return this.dayOfMonth;
    }

    /**
     * Converts day of month to string.
     * @return  string representation for day of month.
     */
    @Override
    public String toString() {
        return Integer.toString(getDayOfMonth());
    }
}
