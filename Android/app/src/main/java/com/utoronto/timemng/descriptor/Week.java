package com.utoronto.timemng.descriptor;

import android.util.Log;

import java.util.Collection;

/**
 * 31/10/2014 15:32.
 */
public class Week {
    private static final String TAG = "c2dm_week";
    private final Collection<Day> days;

    /**
     * Default constructor for this class.
     * @param days  A collection of days in the week.
     */
    public Week(final Collection<Day> days) {
        super();
        if (null != days && !days.isEmpty()) {
            this.days = days;
        } else {
            Log.i(TAG, "the days collection cannot be null.");
            throw new IllegalArgumentException("the days collection cannot be null");
        }
    }

    /**
     * Gets the days in this week.
     * @return  a collection of days for this week.
     */
    public Collection<Day> getDays() {
        return this.days;
    }
}
