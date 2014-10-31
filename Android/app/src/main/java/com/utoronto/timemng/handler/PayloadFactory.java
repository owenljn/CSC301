package com.utoronto.timemng.handler;


import com.utoronto.timemng.descriptor.Year;

/**
 * Date: 2014-10-26.
 */
public class PayloadFactory {

    public PayloadHandler create(final String payload) {
        // TODO: 1. De-serialize payload into a POJO class "PayloadDescriptor.class"

        // TODO: provide different handlers based on the content of the payload...
        Year year = new Year();
        return new CreateEventPayloadHandler(payload, year);
    }
}
