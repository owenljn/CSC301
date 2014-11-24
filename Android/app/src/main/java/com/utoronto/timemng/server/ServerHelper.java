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

    public static void sendToServer(final String eventName, final String note,
                                    final String location, final String startYear,
                                    final String startMonth, final String startDay,
                                    final String startTime, final String finishYear,
                                    final String finishMonth, final String finishDay,
                                    final String finishTime, final String isAllDay) {
        // TODO: Implement sending to server.
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
