package com.utoronto.timemng.handler;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.utoronto.timemng.descriptor.Month;

/**
 * Date: 2014-10-26.
 */
public class CreateEventPayloadHandler implements PayloadHandler {

    private static final String TAG = "c2dm_task";
    private final String payload;
    private final Month month;

    //TODO: Remove payload from the constructor. It's here for testing purposes for now.
    public CreateEventPayloadHandler(final String payload, Month month) {
        this.payload = payload;
        this.month = month;
    }

    @Override
    public void execute(final Context context) {
        Log.d(TAG, "Received Message: " + this.payload);

        // show temporary message on the screen.... delete this after
        Toast.makeText(context, "MSG: " + this.payload, Toast.LENGTH_LONG).show();

        // TODO: do something with this payload...
    }
}
