package com.utoronto.timemng.descriptor;

import android.util.Log;

import java.util.Collection;

/**
 * Contains the information for the specified year.
 */
public class Year {
    private static final String TAG = "Year";
    private final int year;
    private final Collection<Month> months;

    /**
     * The default constructor for this class.
     * @param year          The year in question.
     * @param whichMonths   A collection of months. Cannot be null or empty.
     */
    public Year(final int year, final Collection<Month> whichMonths) {
        if (null != whichMonths && !whichMonths.isEmpty()) {
            this.year = year;
            this.months = whichMonths;
        } else {
            Log.i(TAG, "List of months is either null or empty.");
            throw new IllegalArgumentException("The list of months cannot be null," +
                    "and must not be empty");
        }
    }

    public int getYear() {
        return this.year;
    }

    public Collection<Month> getMonths() {
        return this.months;
    }
}
