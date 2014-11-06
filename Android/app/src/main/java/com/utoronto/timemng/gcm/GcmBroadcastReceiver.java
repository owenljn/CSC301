package com.utoronto.timemng.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.utoronto.timemng.calendar.CalendarHelper;
import com.utoronto.timemng.calendar.CalendarMonthConstructor;
import com.utoronto.timemng.event.DayDto;
import com.utoronto.timemng.event.PayloadContainerDto;

import java.util.List;

/**
 * Receives push notifications from the Google server.
 */
public class GcmBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "c2dm receiver";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        // Only care about messages from the google server.
        if (null != intent.getAction() && intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
            Log.d(TAG, intent.getAction());
            Log.d(TAG, intent.getStringExtra("payload"));
            if (null != intent.getExtras()) {
                final Bundle bundle = intent.getExtras(); // Get bundle from intent.

                final String payload = bundle.getString("payload"); // Retrieve payload from bundle.
                // De-serialise payload.
                final PayloadContainerDto dto = CalendarHelper.deserializePayload(payload);
                // If de-serialisation went well, get singleton instance of CalendarMonthConstructor,
                // and tell it to populate itself with the events that just arrived in payload.
                if (null != dto) {
                    final List<DayDto> days = dto.getDays(); // Get list of Day objects.
                    final CalendarMonthConstructor calendar = CalendarMonthConstructor.getInstance();
                    calendar.populateWithEvents(days);
                }
            }
        }
    }
}