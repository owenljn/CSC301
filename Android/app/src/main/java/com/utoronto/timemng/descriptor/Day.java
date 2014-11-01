package com.utoronto.timemng.descriptor;

import android.util.Log;
import com.utoronto.timemng.descriptor.type.WhichDay;

import java.util.Collection;

/**
 * Contains information for the specified day.
 */
public class Day {
    private static final String TAG = "c2dm_day";
    private final int dayOfMonth;
    private final WhichDay day;
    private final Collection<Event> events;

    /**
     * The default constructor for the class.
     * @param day       the type of day of the week.
     * @param events    the collection of event classes.
     */
    public Day(final int dayOfMonth, final WhichDay day, final Collection<Event> events) {
        super();
        if (null != day && null != events && !events.isEmpty()) {
            this.dayOfMonth = dayOfMonth;
            this.day = day;
            this.events = events;
        } else {
            Log.i(TAG, "Day of the week cannot be null, and events cannot be null");
            throw new IllegalArgumentException("Neither day of the week nor event can be null");
        }
    }

    /**
     * Gets the day of the month.
     * @return  day of the month.
     */
    public int getDayOfMonth() {
        return this.dayOfMonth;
    }

    /**
     * Gets the type for day of the week.
     * @return  type for day of the week.
     */
    public WhichDay getDay() {
        return this.day;
    }

    /**
     * Gets the collection of events for the day.
     * @return  colection of events for the day.
     */
    public Collection<Event> getEvents() {
        return this.events;
    }
}
