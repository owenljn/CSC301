package com.utoronto.timemng.handler;

import java.util.ArrayList;
import java.util.Scanner;

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
        
        //ArrayList<String> parameters = new ArrayList<String>();
       // Scanner src = new Scanner(payload);
        //src.useDelimiter(";");
        //while (src.hasNext()) {
        //	parameters.add(src.next());
        //}
        //Log.d(TAG, parameters);
        
        // show temporary message on the screen.... delete this after
        Toast.makeText(context, "MSG: " + this.payload, Toast.LENGTH_LONG).show();

        // TODO: do something with this payload...
    }
}
