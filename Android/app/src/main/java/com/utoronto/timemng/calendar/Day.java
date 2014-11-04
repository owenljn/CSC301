package com.utoronto.timemng.calendar;

import android.util.Log;
import com.utoronto.timemng.descriptor.Event;

import java.util.Calendar;
import java.util.Collection;

/**
 * 02/11/2014 13:33.
 */
// TODO: modify this so that it doesn't store events, etc.
public class Day {
    private static final String TAG = "c2dm_Day";
    private final Calendar dayDate;
    private final String dateString;
    private Collection<Event> events;

    /**
     * Carries information for the calendar day.
     * @param dayDate   the date for this day.
     */
    public Day(final Calendar dayDate, final String dateString) {
        super();
        this.dayDate = dayDate;
        this.dateString = dateString;
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
    public String getDateString() {
        return this.dateString;
    }

    /**
     * Sets the events for the day.
     * @param events    a collection of events.
     */
    public final void setEvents(Collection<Event> events) {
        if (null != events && !events.isEmpty()) {
            this.events = events;
        } else {
            Log.i(TAG, "events cannot be set to null, and cannot be empty");
            throw new IllegalArgumentException("events cannot be set to null, and cannot be empty");
        }
    }

    /**
     * Gets the events for the day.
     * @return  a collection of events. Null if there are no events booked for the day.
     */
    public final Collection<Event> getEvents() {
        return this.events;
    }

    @Override
    public String toString() {
        return getDateString();
    }
}
