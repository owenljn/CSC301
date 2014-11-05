package com.utoronto.timemng.calendar;
import android.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utoronto.timemng.event.PayloadContainerDto;

import java.io.IOException;
import java.util.Calendar;


/**
 * Contains several helper methods for the calendar.
 */
public class CalendarHelper {
    private static final String TAG = "c2dm de-serialise";
    // Create a new object mapper for de-serialising PayloadContainerDto objects.
    // Used by calendar to obtain events that will be inserted into it.
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Private constructor for this class, as this class only has static methods.
     */
    private CalendarHelper() {
    }

    /**
     * Gets the offset for the given month. Allows to map java calendar numbering for days of
     * the week to our numbering for days of the week.
     * @param month relevant month.
     * @return      offset.
     */
    public static int getOffset(final Calendar month) {
        int shiftBy = month.get(Calendar.DAY_OF_WEEK); // Day of the week for first day of the month.
        // On our calendar SUNDAY is 6, so we have to go 6 days back from first day of current month
        // to get the date of the previous month that corresponds to Monday.
        if (Calendar.SUNDAY == shiftBy) {
            shiftBy = 6;
            // On java calendar MONDAY through SATURDAY are numbered as such: 2, 3, ..., 7. On our calendar
            // MONDAY through SATURDAY are numbered as such: 0, 1, ..., 5. The java calendar is two positions
            // ahead of ours (for days of the week numbering), so to convert the weekday number of first day
            // of the month from their numbering system to ours, we need to subtract 2 from their weekday for
            // first day of month to get corresponding weekday in our numbering system.
        } else {
            shiftBy = shiftBy - 2;
        }
        return shiftBy;
    }

    /**
     * Attempt to de-serialise payload. If the format is not as expected, will not be successful.
     * @param payload   JSON string representing payload.
     * @return          a DTO containing the list of days.
     */
    public static PayloadContainerDto deserializePayload(final String payload) {
        PayloadContainerDto container = null; // Initialise our PayloadContainerDto class.
        try { // Try to de-serialise the class into the PayloadContainerDto object with all the parameters.
            container = OBJECT_MAPPER.readValue(payload, PayloadContainerDto.class);
        } catch (final IOException e) {
            // Don't need to stop the program if payload cannot be de-serialised. Could have been
            // bad payload. Log the event anyway.
            Log.i(TAG, "Could not de-serialise payload: "+ payload);
        }
        return container;
    }
}
