package com.utoronto.timemng.daytasks;

import android.app.Activity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.utoronto.timemng.app.R;
import com.utoronto.timemng.event.EventDto;

import java.util.Calendar;
import java.util.List;

/**
 * Constructs a ListView object of events for the selected day of the month.
 */
public class EventListConstructor {
    /**
     * A tag for logging purposes.
     */
    private static final String TAG = "c2dm event list";
    private final Activity activity;
    private final List<EventDto> events;
    private final int year;
    private final int month;
    private final int day;
    private final Calendar thisCal;

    /**
     * Constructs a list of events.
     * @param activity      parent activity.
     * @param events        list of events for the day.
     * @param year          the year the events start.
     * @param month         the month the events start.
     * @param day           the day the events start.
     */
    public EventListConstructor(final Activity activity, final List<EventDto> events,
                                final int year, final int month, final int day) {
        super();
        this.activity = activity;
        this.events = events;
        this.year = year;
        this.month = month;
        this.day = day;
        this.thisCal = Calendar.getInstance();
        this.thisCal.set(Calendar.YEAR, year);
        this.thisCal.set(Calendar.MONTH, month);
        this.thisCal.set(Calendar.DAY_OF_MONTH, day);
        // Create an adapter.
        final EventListAdapter adapter = new EventListAdapter(activity, events);
        // Find the list view.
        final ListView listView = (ListView) activity.findViewById(R.id.event_list);
        // Set date.
        whichDateString();
        // Set adapter.
        listView.setAdapter(adapter);
        // Set a click listener.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                final EventDto tag = (EventDto) view.getTag();
                Toast.makeText(activity.getApplicationContext(), tag.getEventTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Sets the date.
     */
    private void whichDateString() {
        // The month and year label on the calendar.
        final TextView dateField = (TextView) this.activity.findViewById(R.id.date_text);
        final String dayOfWeek = DateFormat.format("EEEE", this.thisCal).toString();
        final String monthStr = DateFormat.format("MMMM", this.thisCal).toString();
        final String dayStr = String.valueOf(this.day);
        final String yearStr = String.valueOf(this.year);
        dateField.setText(String.format("%s, %s %s, %s", dayOfWeek, monthStr, dayStr, yearStr));
    }
}
