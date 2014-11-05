package com.utoronto.timemng.daytasks;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.utoronto.timemng.event.EventDto;

import java.util.List;

/**
 * Grid adapter for a ListView. Used for constructing a list of events.
 */
public class EventListAdapter extends BaseAdapter {
    private final Activity activity;
    private final List<EventDto> events;

    public EventListAdapter(final Activity activity, final List<EventDto> events) {
        this.activity = activity;
        this.events = events;
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
     * Gets the id of an event at some position.
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
        return null;
    }
}
