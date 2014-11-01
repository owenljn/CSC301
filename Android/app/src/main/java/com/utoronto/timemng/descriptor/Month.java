package com.utoronto.timemng.descriptor;

import android.util.Log;
import com.utoronto.timemng.descriptor.type.WhichMonth;

import java.util.Collection;

/**
 * Contains information for the specified month.
 */
public class Month {
    private static final String TAG = "c2dm_month";
    private final WhichMonth month;
    private final Collection<Week> weeks;

    /**
     * The default constructor.
     * @param month the month.
     * @param weeks a collection of weeks for this month.
     */
    public Month(final WhichMonth month, final Collection<Week> weeks) {
        super();
        if (null != month && null != weeks && !weeks.isEmpty()) {
            this.month = month;
            this.weeks = weeks;
        } else {
            Log.i(TAG, "Month cannot be null, and neither can weeks");
            throw new IllegalArgumentException("month and weeks cannot be null.");
        }
    }

    /**
     * Gets the month.
     * @return  the hard type for month.
     */
    public WhichMonth getMonth() {
        return this.month;
    }

    /**
     * Gets the weeks for this month.
     * @return  a collection of Week classes.
     */
    public Collection<Week> getWeeks() {
        return this.weeks;
    }
}
