package com.utoronto.timemng.calendar;

import java.util.Calendar;

/**
 * 02/11/2014 13:33.
 */
// TODO: modify this so that it doesn't store events, etc.
public class DayCell {
    private static final String TAG = "c2dm daycell";
    private final Calendar dayDate;
    private final int dateInt;

    /**
     * Carries information for the calendar day.
     * @param dayDate   the date for this day.
     */
    public DayCell(final Calendar dayDate, final int dateInt) {
        super();
        this.dayDate = dayDate;
        this.dateInt = dateInt;
    }

    /**
     * Gets Calendar object with relevant date.
     * @return  calendar object for this day.
     */
    public Calendar getDayDate() {
        return this.dayDate;
    }

    /**
     * Gets the string number representation of this day of month.
     * @return  string representation of day of month for this day.
     */
    public int getDateInt() {
        return this.dateInt;
    }

    @Override
    public String toString() {
        return Integer.toString(getDateInt());
    }
}
