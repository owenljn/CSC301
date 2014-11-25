package com.utoronto.timemng.server;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * 23/11/2014 17:08.
 */
public final class ServerHelper {
    private ServerHelper() {
        super();
    }

    /**
     * Create an array of variables necessary for event creation in database, and send
     * to server.
     * @param eventName     name of the event; not null.
     * @param note          note for the event.
     * @param location      location for the event.
     * @param startYear     start year for the event; not null.
     * @param startMonth    start month for the event; not null.
     * @param startDay      start day of month for the event; not null.
     * @param startTime     start time for the event; not null.
     * @param finishYear    year the event finishes; not null.
     * @param finishMonth   month the event finishes; not null.
     * @param finishDay     day the event finishes; not null.
     * @param finishTime    time the event finishes; not null.
     * @param isAllDay      true iff the event is all-day.
     */
    public static void createEvent(final String eventName, final String note,
                                   final String location, final String startYear,
                                   final String startMonth, final String startDay,
                                   final String startTime, final String finishYear,
                                   final String finishMonth, final String finishDay,
                                   final String finishTime, final String isAllDay) {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


        nameValuePairs.add(new BasicNameValuePair("eventname", eventName )); // event name
        nameValuePairs.add(new BasicNameValuePair("des", note)); // note
        nameValuePairs.add(new BasicNameValuePair("loc", location)); // location
        nameValuePairs.add(new BasicNameValuePair("sy", startYear)); // start year
        nameValuePairs.add(new BasicNameValuePair("sm", startMonth)); // start month
        nameValuePairs.add(new BasicNameValuePair("sd", startDay)); // start day
        nameValuePairs.add(new BasicNameValuePair("st", startTime)); // start time
        nameValuePairs.add(new BasicNameValuePair("fy", finishYear)); // finish year
        nameValuePairs.add(new BasicNameValuePair("fm", finishMonth)); // finish month
        nameValuePairs.add(new BasicNameValuePair("fd", finishDay)); // finish day
        nameValuePairs.add(new BasicNameValuePair("ft", finishTime)); // finish time
        nameValuePairs.add(new BasicNameValuePair("settings", isAllDay)); // is all day?

        new AddFileTask().execute(nameValuePairs);
    }
}
