package com.utoronto.timemng.daytasks;

import android.app.Activity;
import android.widget.ListView;
import com.utoronto.timemng.app.R;
import com.utoronto.timemng.event.EventDto;

import java.util.List;

/**
 * Constructs a ListView object of events for the selected day of the month.
 */
public class EventListConstructor {
    /**
     * A tag for logging purposes.
     */
    private static final String TAG = "c2dm event list";

    public EventListConstructor(final Activity activity, final List<EventDto> events) {
        super();
        // Find the list view.
        final ListView listView = (ListView) activity.findViewById(R.id.event_list);
        // Create an adapter.
        final EventListAdapter adapter = new EventListAdapter(activity, events);
        // Set adapter.
        listView.setAdapter(adapter);
    }
}
