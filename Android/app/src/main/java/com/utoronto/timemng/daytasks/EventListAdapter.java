package com.utoronto.timemng.daytasks;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.utoronto.timemng.app.R;
import com.utoronto.timemng.event.EventDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Grid adapter for a ListView. Used for constructing a ListView object of events.
 */
public class EventListAdapter extends BaseAdapter {

    /**
     * The activity.
     */
    private final Activity activity;

    /**
     * List of events that will be used to construct an adapter.
     */
    private final List<EventDto> events;

    /**
     * Default constructor for this class.
     * @param activity  the activity that is used.
     * @param events    the list of events that will be used to construct an adapter.
     */
    public EventListAdapter(final Activity activity, final List<EventDto> events) {
        this.activity = activity;
        // If there are no events, just create an empty array list.
        // TODO: If no events, display message saying so.
        if (null != events) {
            this.events = events;
        } else {
            this.events = new ArrayList<EventDto>();
        }
    }

    /**
     * Gets the number of events for the day.
     * @return  number of events for selected day.
     */
    @Override
    public int getCount() {
        return this.events.size();
    }

    /**
     * Gets the event at given position.
     * @param position  Position of event in the list.
     * @return          the event at given position.
     */
    @Override
    public Object getItem(final int position) {
        return this.events.get(position);
    }

    /**
     * Gets the id of an event at some position. This id will be set by the server upon
     * the creation of the Event object.
     * @param position  position of the event in the list.
     * @return          id of an event at given position.
     */
    @Override
    public long getItemId(final int position) {
        return this.events.get(position).getEventId();
    }

    /**
     * Gets the view object of the row of the ListView item.
     * @param position      the row in the ListView.
     * @param convertView   the view that will be reused.
     * @param parent        parent view group.
     * @return              the view in question.
     */
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View row = convertView;
        final TextView textView;
        final EventDto event = this.events.get(position); // Get the event that will be stored in this row.
        final long eventId = event.getEventId(); // Get the unique event id.
        // Layout hasn't been inflated yet because this is the first row that is being looked at.
        if (null == row) {
            row = LayoutInflater.from(this.activity.getApplicationContext()).inflate(R.layout.list_item, parent, false);
        }

        textView = (TextView) row.findViewById(R.id.list_item); // Get the TextView that will be applied.
        textView.setTextColor(Color.BLACK); // Want the colour of the text to be black.
        textView.setText(event.getEventTitle()); // Set the event title.
        row.setTag(eventId); // Tag the row with the event id.
        return row;
    }
}
