package com.utoronto.timemng.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Contains information for one month day.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DayDto {

    private final int year;
    private final int month;
    private final int day;
    private final List<EventDto> events;
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";
    private static final String EVENTS = "events";

    /**
     * Default constructor for this class.
     * @param year      the year for this day.
     * @param month     the month number.
     * @param day       the day number.
     * @param events    list of events for this day.
     */
    @JsonCreator
    public DayDto(@JsonProperty(YEAR) final int year,
                  @JsonProperty(MONTH) final int month,
                  @JsonProperty(DAY) final int day,
                  @JsonProperty(EVENTS) final List<EventDto> events) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.events = events;
    }

    /**
     * Gets the year for this day.
     * @return  the string representation of the year.
     */
    @JsonProperty(YEAR)
    public int getYear() {
        return this.year;
    }

    /**
     * Gets the month for this day.
     * @return  the string representation of the month.
     */
    @JsonProperty(MONTH)
    public int getMonth() {
        return this.month;
    }

    /**
     * Gets the day number.
     * @return  the string representation of the day number.
     */
    @JsonProperty(DAY)
    public int getDay() {
        return this.day;
    }

    /**
     * Gets the list of events.
     * @return  list of event classes.
     */
    @JsonProperty(EVENTS)
    public List<EventDto> getEvents() {
        return this.events;
    }
}
