package com.utoronto.timemng.handler;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.utoronto.timemng.descriptor.Event;

import java.util.Collection;

/**
 * Date: 2014-10-26.
 */
public class CreateEventPayloadHandler implements PayloadHandler {

    private static final String TAG = "c2dm_task";
    private final String payload;
    private final Collection<Event> events;

    //TODO: Remove payload from the constructor. It's here for testing purposes for now.
    public CreateEventPayloadHandler(final String payload, Collection<Event> events) {
        this.payload = payload;
        this.events = events;
    }

    @Override
    public void execute(final Context context) {
        TextView textBox = new TextView(context.getApplicationContext());
        textBox.setText(this.payload);
//        RelativeLayout l1 = (RelativeLayout) findViewById(R.id.main_layout);
//        l1.addView(textBox);
        Log.d(TAG, "Received Message: " + this.payload);

        // show temporary message on the screen.... delete this after
        Toast.makeText(context.getApplicationContext(), "MSG: " + this.payload, Toast.LENGTH_LONG).show();

        // TODO: do something with this payload...
    }
}
