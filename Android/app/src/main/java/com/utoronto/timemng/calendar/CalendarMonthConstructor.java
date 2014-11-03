package com.utoronto.timemng.calendar;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.utoronto.timemng.app.R;
import com.utoronto.timemng.deserializer.DeserializePayload;
import com.utoronto.timemng.event.Event;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class CalendarMonthConstructor extends LinearLayout {
    // TODO: Make receiver inner class for this.
    private static final String TAG = "c2dm_CalendarMonthConstructor";
    private Calendar selMonth;
    private CalendarGridAdapter adapter;
    private Activity activity;

    /**
     * Default constructor for this class.
     * @param context    application context
     * @param activity   activity.
     */
    public CalendarMonthConstructor(final Context context, final Activity activity) {
        super(context); // Call to default constructor for LinearLayout
        this.activity = activity;
        this.selMonth = Calendar.getInstance(); // Get this month calendar.
        this.adapter = new CalendarGridAdapter(activity, this.selMonth); // Create an adapter.
        final GridView gridView = (GridView) activity.findViewById(R.id.calendar_grid); // My calendar grid.

        whichMonthString();
        gridView.setAdapter(this.adapter);
        // TODO: return a map of day of month to position.
        // TODO: request event list for the month from server.
        // TODO: mark days of month that contain an event.
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(CalendarMonthConstructor.this.activity.getApplicationContext(),
//                        "" + position, Toast.LENGTH_SHORT).show();

                Toast.makeText(CalendarMonthConstructor.this.activity.getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Optional constructor for this class.
     * @param context   application context.
     */
    public CalendarMonthConstructor(final Context context) {
        super(context, null);
    }

    /**
     * Returns string representation of selected month and year.
     */
    private void whichMonthString() {
        final TextView monthName = (TextView) this.activity.findViewById(R.id.month_text);
        final CharSequence month = DateFormat.format("MMMM", this.selMonth); // month
        final CharSequence year = DateFormat.format("yyyy", this.selMonth); // year
        monthName.setText(String.format("%s %s", month.toString(), year.toString()));
    }

    /**
     * Sets a different month. This requires the calendar to be redrawn.
     * @param month selected month.
     */
    public final void setMonth(final Calendar month) {
        // Don't want to redrawn unnecessarily.
        if (this.selMonth.get(Calendar.YEAR) != month.get(Calendar.YEAR) &&
                this.selMonth.get(Calendar.MONTH) != month.get(Calendar.MONTH)) {
            this.selMonth = month;
            // TODO: return a map of day of month to position.
            // TODO: request updated event list for new month from server.
            // TODO: mark days of month that contain an event.
            refactorCalendar(); // Redraw the calendar.
            whichMonthString(); // Get the correct month name.
        }
    }

    /**
     * Gets the calendar object for the currently displayed month.
     * @return  calendar object for the currently displayed month.
     */
    public final Calendar getMonth() {
        return this.selMonth;
    }

    /**
     * Redraws the calendar based on the changes made.
     */
    private void refactorCalendar() {
        this.adapter.makeCalendarArray();
    }

    /**
     * Receives push notifications from the Google server.
     */
    public class GcmBroadcastReceiver extends BroadcastReceiver {

        private static final String TAG = "c2dm receiver";
        private final Map<String, List<Event>> updatedDays = new HashMap<String, List<Event>>();

        @Override
        public void onReceive(final Context context, final Intent intent) {

            if (null != intent.getAction() && intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
                Log.d(TAG, intent.getAction());
                Log.d(TAG, intent.getStringExtra("payload"));
                if (null != intent.getExtras()) {
                    final Bundle bundle = intent.getExtras();

                    final String payload = bundle.getString("payload");
                    final List<Event> events = DeserializePayload.deserialize(payload);

//                    final PayloadFactory messageFactory = new PayloadFactory();
//                    final PayloadHandler handler = messageFactory.create(payload);
//
//                    Log.d(TAG, "Handler: "+handler.getClass().getSimpleName());
//                    handler.execute(context.getApplicationContext());
                }

            }
        }
    }

}
