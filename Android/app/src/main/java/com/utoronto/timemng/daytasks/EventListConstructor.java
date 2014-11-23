package com.utoronto.timemng.daytasks;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
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

    /**
     * Constructs a list of events.
     * @param activity  activity.
     * @param events    list of events.
     */
    public EventListConstructor(final Activity activity, final List<EventDto> events) {
        super();
        // Create an adapter.
        final EventListAdapter adapter = new EventListAdapter(activity, events);
        // Find the list view.
        final ListView listView = (ListView) activity.findViewById(R.id.event_list);
        // Set adapter.
        listView.setAdapter(adapter);
        // Set a click listener.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, long id) {

            }
        });
    }
}
