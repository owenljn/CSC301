package com.utoronto.timemng.handler;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Date: 2014-10-26.
 */
public class CreateTaskPayloadHandler implements PayloadHandler {

    private static final String TAG = "c2dm_task";
    private final String payload;

    public CreateTaskPayloadHandler(final String payload) {
        this.payload = payload;
    }

    @Override
    public void execute(final Context context) {
        Log.d(TAG, "Received Message: " + this.payload);

        // show temporary message on the screen.... delete this after
        Toast.makeText(context, "MSG: " + this.payload, Toast.LENGTH_LONG).show();

        // TODO: do something with this payload...
    }
}
