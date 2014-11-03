package com.utoronto.timemng.event;

import java.util.Collection;
import java.util.List;

/**
 * Contains information for the specified event.
 */
public class Event {
    private static final String TAG = "c2dm_event";
    private final String eventId;
    private final String eventTitle;
    private final String year;
    private final String month;
    private final String dayOfMonth;
    private final String dayOfWeek;
    private final String startTime;
    private final String endTime;
    private String location;
    private String description;
    private Collection<String> inviteeEmails;
    private boolean recurring;
    private List<WeekDay> recursOn;
    private boolean isAllDay;

    public Event(final String eventId, final String eventTitle, final String year,
                 final String month, final String dayOfMonth, final String dayOfWeek,
                 final String startTime, final String endTime) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.recurring = false;
    }

    /**
     * Updates the location of the event.
     * @param location  string representation of the location of event.
     */
    public final void setLocation(final String location) {
        if (null != location) {
            this.location = location;
        }
    }

    /**
     * Updates the description of the event.
     * @param description   string representation of description of event.
     */
    public final void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Sets the collection of invitees.
     * @param inviteeEmails collection of strings for invitee emails.
     */
    public final void setInviteeEmails(final Collection<String> inviteeEmails) {
        if (null != inviteeEmails && !inviteeEmails.isEmpty()) {
            this.inviteeEmails = inviteeEmails;
        }
    }

    /**
     * Sets the weekdays the event recurs on if it is set as recurring.
     * @param recurring true iff the event is recurring.
     * @param recursOn  weekdays the event recurs on.
     *                  not null or empty if recurring is true.
     */
    public final void setRecurring(final boolean recurring, final List<WeekDay> recursOn) {
        if (recurring && null != recursOn && !recursOn.isEmpty()) {
            this.recurring = recurring;
            this.recursOn = recursOn;
        }
    }

    /**
     * Marks the event as all-day.
     * @param isAllDay  is set to true iff this is an all-day event.
     */
    public final void setAllDay(final boolean isAllDay) {
        this.isAllDay = isAllDay;
    }

    /**
     * @return true iff the event is recurring.
     */
    public final boolean isRecurring() {
        return this.recurring;
    }

    /**
     * Get the id of the event.
     * @return  the event's unique id.
     */
    public final String getEventId() {
        return this.eventId;
    }

    /**
     * Get the title of the event.
     * @return  the title of the event.
     */
    public final String getEventTitle() {
        return this.eventTitle;
    }

    /**
     * Gets the year of the event.
     * @return  string representation of the year for the event.
     */
    public final String getYear() {
        return this.year;
    }

    /**
     * Gets the month of the event.
     * @return  string representation of the month for the event.
     */
    public final String getMonth() {
        return this.month;
    }

    /**
     * Gets the day of month of the event.
     * @return  string representation of the day of month.
     */
    public final String getDayOfMonth() {
        return this.dayOfMonth;
    }

    /**
     * Gets the day of week for the event.
     * @return  string representation of the day of week.
     */
    public final String getDayOfWeek() {
        return this.dayOfWeek;
    }

    /**
     * Gets the start time for the event.
     * @return  string representation of the start time.
     */
    public final String getStartTime() {
        return this.startTime;
    }

    /**
     * Gets the finish time for the event.
     * @return  sting representation of the finish time for the event.
     */
    public final String getEndTime() {
        return this.endTime;
    }

    /**
     * Gets the location of the event.
     * @return  string representation of the location of the event.
     */
    public final String getLocation() {
        return this.location;
    }

    /**
     * Gets the description of the event.
     * @return  string representation of the description of the event.
     */
    public final String getDescription() {
        return this.description;
    }

    /**
     * Gets the list of the invitee emails.
     * @return  list of strings for invitee emails.
     */
    public final Collection<String> getInviteeEmails() {
        return this.inviteeEmails;
    }

    /**
     * Gets the days of week on which the event will reoccur.
     * @return  type for day of week
     */
    public final List<WeekDay> getRecursOn() {
        return this.recursOn;
    }

    /**
     * @return  true iff this is an all-day event.
     */
    public final boolean isAllDay() {
        return this.isAllDay;
    }
}
