package com.utoronto.timemng.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utoronto.timemng.calendar.CalendarMonthConstructor;
import com.utoronto.timemng.event.DayDto;
import com.utoronto.timemng.event.PayloadContainerDto;

import java.io.IOException;
import java.util.List;

/**
 * Receives push notifications from the Google server.
 */
public class GcmBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "c2dm receiver";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void onReceive(final Context context, final Intent intent) {

        if (null != intent.getAction() && intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
            Log.d(TAG, intent.getAction());
            Log.d(TAG, intent.getStringExtra("payload"));
            if (null != intent.getExtras()) {
                final Bundle bundle = intent.getExtras();

                final String payload = bundle.getString("payload");
                final PayloadContainerDto dto = deserializePayload(payload);
                if (null != dto) {
                    final List<DayDto> days = dto.getDays();
                    CalendarMonthConstructor calendar = CalendarMonthConstructor.getInstance();
                    calendar.populateWithEvents(days);
                }
            }
        }
    }

    /**
     * Attempt to de-serialise payload. If the format is not as expected, will not be successfull.
     * @param payload   JSON string representing payload.
     * @return          a DTO containing the list of days.
     */
    private static PayloadContainerDto deserializePayload(final String payload) {
        PayloadContainerDto container = null;
        try {
            container = OBJECT_MAPPER.readValue(payload, PayloadContainerDto.class);
        } catch (final IOException e) {
            Log.i(TAG, "Could not de-serialise payload.");
        }
        return container;
    }
}