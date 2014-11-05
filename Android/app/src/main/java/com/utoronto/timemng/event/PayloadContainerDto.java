package com.utoronto.timemng.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Contains the type of action (e.g. create, delete, update event), and the month.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayloadContainerDto {

    private static final String DAYS = "days";
    private final List<DayDto> days;

    /**
     * The default constructor.
     * @param days        list of events.
     */
    @JsonCreator
    public PayloadContainerDto(@JsonProperty(DAYS) final List<DayDto> days) {
        if (null != days) {
            this.days = days;
        } else {
            throw new IllegalArgumentException("Cannot construct PayloadDescriptor" +
                    " with events set to null");
        }
    }

    /**
     * Gets the month for the action.
     * @return  the Year object.
     */
    @JsonProperty(DAYS)
    public List<DayDto> getDays() {
        return this.days;
    }
}
