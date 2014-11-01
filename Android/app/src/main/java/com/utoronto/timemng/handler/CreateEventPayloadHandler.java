package com.utoronto.timemng.handler;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.utoronto.timemng.app.R;
import com.utoronto.timemng.descriptor.Year;

/**
 * Date: 2014-10-26.
 */
public class CreateEventPayloadHandler implements PayloadHandler {

    private static final String TAG = "c2dm_task";
    private final String payload;
    private final Year year;

    //TODO: Remove payload from the constructor. It's here for testing purposes for now.
    public CreateEventPayloadHandler(final String payload, Year year) {
        this.payload = payload;
        this.year = year;
    }

    @Override
    public void execute(final Context context) {
//        TextView textBox = new TextView(context);
//        textBox.setText(this.payload);
//        RelativeLayout l1 = (RelativeLayout) findViewById(R.id.activity_core);
//        l1.addView(textBox);

        Log.d(TAG, "Received Message: " + this.payload);

        // show temporary message on the screen.... delete this after
        Toast.makeText(context, "MSG: " + this.payload, Toast.LENGTH_LONG).show();

        // TODO: do something with this payload...
    }
}
