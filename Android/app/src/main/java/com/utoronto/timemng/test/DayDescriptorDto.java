package com.utoronto.timemng.test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Alina Gvozdik
 * 04/11/2014 18:35.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DayDescriptorDto {
    private String name;
    private List<String> list;

    @JsonCreator
    public DayDescriptorDto(@JsonProperty("name") final String name,
                            @JsonProperty("list") final List<String> list) {
        this.name = name;
        this.list = list;
    }

    @JsonProperty("name")
    public String getName() {
        return this.name;
    }

    @JsonProperty("list")
    public List<String> getList() {
        return this.list;
    }
}
