package com.utoronto.timemng.daytasks;

import android.app.Activity;
import android.widget.ListView;
import com.utoronto.timemng.app.R;
import com.utoronto.timemng.event.EventDto;

import java.util.List;

/**
 * Constructs a list of events for the selected day of the month.
 */
public class EventListConstructor {
    private static final String TAG = "c2dm event list";
    private final Activity activity;
    private final List<EventDto> events;
    private final EventListAdapter adapter;
    private final ListView listView;

    public EventListConstructor(final Activity activity, List<EventDto> events) {
        this.activity = activity;
        this.events = events;
        this.listView = (ListView) activity.findViewById(R.id.event_list);
        this.adapter = new EventListAdapter(activity, events);
        this.listView.setAdapter(this.adapter);
    }
}
