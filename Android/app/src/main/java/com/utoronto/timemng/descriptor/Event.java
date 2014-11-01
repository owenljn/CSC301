package com.utoronto.timemng.descriptor;

import android.util.Log;
import com.utoronto.timemng.descriptor.type.WhichDay;

import java.util.Collection;

/**
 * Contains information for the specified event.
 */
public class Event {
    private static final String TAG = "c2dm_event";
    private String eventName;
    private int startHour;
    private int startMin;
    private int endHour;
    private int endMin;
    private String location;
    private boolean recurring;
    private Collection<WhichDay> recursOn;

    /**
     * Additional constructor.
     * @param eventName name of event.
     * @param location  location of event.
     * @param recurring true iff event is recurring.
     * @param recursOn  days event is repeated on.
     */
    public Event(final String eventName, final String location,
                 final boolean recurring, final Collection<WhichDay> recursOn) {
        this(eventName, 0, 0, 0, 0, location, recurring, recursOn);
    }

    /**
     * Default constructor.
     * @param eventName name of event.
     * @param startHour start hour of event.
     * @param startMin  start minute of event.
     * @param endHour   end hour of event.
     * @param endMin    end minute of event.
     * @param location  location of event.
     * @param recurring true if event is recurring.
     * @param recursOn  days the event repeats on.
     */
    public Event(final String eventName, final int startHour, final int startMin,
                 final int endHour, final int endMin, final String location,
                 final boolean recurring, final Collection<WhichDay> recursOn) {
        super();
        if (null != eventName) {
            this.eventName = eventName;
            this.startHour = startHour;
            this.startMin = startMin;
            this.endHour = endHour;
            this.endMin = endMin;
            this.location = location;
            this.recurring = recurring;
            this.recursOn = recursOn;
        } else {
            Log.i(TAG, "The name of the event was set to null.");
            throw new IllegalArgumentException("The name of the event cannot be null");
        }
    }

    /**
     * Gets the name of the event.
     * @return  name of event.
     */
    public String getEventName() {
        return this.eventName;
    }

    /**
     * Gets the start hour of event.
     * @return  start hour.
     */
    public int getStartHour() {
        return this.startHour;
    }

    /**
     * Gets the start minute of the event.
     * @return  start minute.
     */
    public int getStartMin() {
        return this.startMin;
    }

    /**
     * Gets the hour the event ends.
     * @return  end hour.
     */
    public int getEndHour() {
        return this.endHour;
    }

    /**
     * Gets the minute the event ends.
     * @return  end minute.
     */
    public int getEndMin() {
        return this.endMin;
    }

    /**
     * Gets the location of the event.
     * @return  the address string.
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * @return  true iff the event is recurring.
     */
    public boolean isRecurring() {
        return this.recurring;
    }

    /**
     * Gets the days of the week on which the event repeats.
     * @return  a collection of the days of the week types.
     */
    public Collection<WhichDay> getRecursOn() {
        return this.recursOn;
    }
}
