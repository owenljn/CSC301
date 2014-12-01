package com.utoronto.timemng.daytasks;

import android.app.Activity;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.utoronto.timemng.app.AddDelUpdEventActivity;
import com.utoronto.timemng.app.R;
import com.utoronto.timemng.app.constants.Constants;
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
    private final EventListAdapter adapter;

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
        this.adapter = new EventListAdapter(activity, events);
        // Find the list view.
        final ListView listView = (ListView) activity.findViewById(R.id.event_list);
        // Set date.
        whichDateString();
        // Set adapter.
        listView.setAdapter(this.adapter);
        // Set a click listener.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                final EventDto tag = (EventDto) view.getTag();
                final Intent intent = new Intent(EventListConstructor.this.activity, AddDelUpdEventActivity.class);
                intent.putExtra("type", "edit");
                intent.putExtra("eventName", tag.getEventTitle());
                intent.putExtra("startYear", String.valueOf(tag.getStartYear()));
                intent.putExtra("startMonth", String.valueOf(tag.getStartMonth()));
                intent.putExtra("startDay", String.valueOf(tag.getStartDayOfMonth()));
                intent.putExtra("startTime", tag.getStartTime());
                intent.putExtra("endYear", String.valueOf(tag.getEndYear()));
                intent.putExtra("endMonth", String.valueOf(tag.getEndMonth()));
                intent.putExtra("endDay", String.valueOf(tag.getEndDayOfMonth()));
                intent.putExtra("endTime", tag.getEndTime());
                intent.putExtra("isAllDay", Boolean.toString(tag.isAllDay()));
                intent.putExtra("description", tag.getDescription());
                intent.putExtra("location", tag.getLocation());
                EventListConstructor.this.activity.startActivityForResult(intent, Constants.REQUEST_EXIT);
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
