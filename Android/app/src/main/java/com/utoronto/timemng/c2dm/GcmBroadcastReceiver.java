package com.utoronto.timemng.c2dm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.utoronto.timemng.handler.PayloadFactory;
import com.utoronto.timemng.handler.PayloadHandler;

/**
 * Date: 2014-10-26.
 */
public class GcmBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "c2dm receiver";

    @Override
    public void onReceive(final Context context, final Intent intent) {

        if (null != intent.getAction() && intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
            Log.d(TAG, intent.getAction());
            Log.d(TAG, intent.getStringExtra("payload"));
            if (null != intent.getExtras()) {
                final Bundle bundle = intent.getExtras();

                final String payload = bundle.getString("payload");

                final PayloadFactory messageFactory = new PayloadFactory();
                final PayloadHandler handler = messageFactory.create(payload);

                Log.d(TAG, "Handler: "+handler.getClass().getSimpleName());
                handler.execute(context.getApplicationContext());
            }

        }
    }
}
