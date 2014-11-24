package com.utoronto.timemng.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.List;

/**
 * Contains information for the specified event.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto {
    private static final String TAG = "c2dm_event";
    private static final String EVENT_ID = "eventId";
    private static final String EVENT_TITLE = "eventTitle";
    private static final String START_YEAR = "startYear";
    private static final String START_MONTH = "startMonth";
    private static final String START_DAY_OF_MONTH = "startDayOfMonth";
    private static final String START_TIME = "startTime";
    private static final String END_YEAR = "endYear";
    private static final String END_MONTH = "endMonth";
    private static final String END_DAY_OF_MONTH = "endDayOfMonth";
    private static final String END_TIME = "endTime";
    private static final String LOCATION = "location";
    private static final String DESCRIPTION = "description";
    private static final String IS_ALL_DAY = "isAllDay";

    // TODO: change startYear, startMonth, startDayOfMonth, same thing for end.
    private final long eventId;
    private final String eventTitle;
    private final int startYear;
    private final int startMonth;
    private final int startDayOfMonth;
    private final String startTime;
    private final int endYear;
    private final int endMonth;
    private final int endDayOfMonth;
    private final String endTime;
    private final String location;
    private final String description;
    private final boolean isAllDay;

    /**
     * Default constructor for this class.
     * @param eventId       the unique id of the event as generated by the server.
     * @param eventTitle    the title of the event; cannot be null.
     * @param startTime     the start time of the event.
     * @param endTime       the finish time of the event.
     * @param location      the location of the event; can be null.
     * @param description   the description of the event; can be null;
     * @param isAllDay      true iff the event is all day.
     */
    @JsonCreator
    public EventDto(@JsonProperty(EVENT_ID) final long eventId, @JsonProperty(EVENT_TITLE) final String eventTitle,
                    @JsonProperty(START_YEAR) final int startYear, @JsonProperty(START_MONTH) final int startMonth,
                    @JsonProperty(START_DAY_OF_MONTH) final int startDayOfMonth,
                    @JsonProperty(START_TIME) final String startTime,
                    @JsonProperty(END_YEAR) final int endYear, @JsonProperty(END_MONTH) final int endMonth,
                    @JsonProperty(END_DAY_OF_MONTH) final int endDayOfMonth,
                    @JsonProperty(END_TIME) final String endTime, @JsonProperty(LOCATION) final String location,
                    @JsonProperty(DESCRIPTION) final String description,
                    @JsonProperty(IS_ALL_DAY) final boolean isAllDay) {
        super();
        if (null != eventTitle) {
            this.eventId = eventId;
            this.eventTitle = eventTitle;
            this.startYear = startYear;
            this.startMonth = startMonth;
            this.startDayOfMonth = startDayOfMonth;
            this.startTime = startTime;
            this.endYear = endYear;
            this.endMonth = endMonth;
            this.endDayOfMonth = endDayOfMonth;
            this.endTime = endTime;
            this.location = location;
            this.description = description;
            this.isAllDay = isAllDay;
        } else {
            throw new IllegalArgumentException("Title of event cannot be null");
        }
    }

    /**
     * Get the id of the event.
     * @return  the event's unique id.
     */
    @JsonProperty(EVENT_ID)
    public final long getEventId() {
        return this.eventId;
    }

    /**
     * Get the title of the event.
     * @return  the title of the event.
     */
    @JsonProperty(EVENT_TITLE)
    public final String getEventTitle() {
        return this.eventTitle;
    }

    /**
     * Gets the year of the event.
     * @return  string representation of the year for the event.
     */
    @JsonProperty(START_YEAR)
    public final int getStartYear() {
        return this.startYear;
    }

    /**
     * Gets the month of the event.
     * @return  string representation of the month for the event.
     */
    @JsonProperty(START_MONTH)
    public final int getStartMonth() {
        return this.startMonth;
    }

    /**
     * Gets the day of month of the event.
     * @return  string representation of the day of month.
     */
    @JsonProperty(START_DAY_OF_MONTH)
    public final int getStartDayOfMonth() {
        return this.startDayOfMonth;
    }

    /**
     * Gets the start time for the event.
     * @return  string representation of the start time.
     */
    @JsonProperty(START_TIME)
    public final String getStartTime() {
        return this.startTime;
    }

    @JsonProperty(END_YEAR)
    public int getEndYear() {
        return this.endYear;
    }

    @JsonProperty(END_MONTH)
    public int getEndMonth() {
        return this.endMonth;
    }

    @JsonProperty(END_DAY_OF_MONTH)
    public int getEndDayOfMonth() {
        return this.endDayOfMonth;
    }

    /**
     * Gets the finish time for the event.
     * @return  sting representation of the finish time for the event.
     */
    @JsonProperty(END_TIME)
    public final String getEndTime() {
        return this.endTime;
    }

    /**
     * Gets the location of the event.
     * @return  string representation of the location of the event.
     */
    @JsonProperty(LOCATION)
    public final String getLocation() {
        return this.location;
    }

    /**
     * Gets the description of the event.
     * @return  string representation of the description of the event.
     */
    @JsonProperty(DESCRIPTION)
    public final String getDescription() {
        return this.description;
    }

    /**
     * @return  true iff this is an all-day event.
     */
    @JsonProperty(IS_ALL_DAY)
    public final boolean isAllDay() {
        return this.isAllDay;
    }
}
