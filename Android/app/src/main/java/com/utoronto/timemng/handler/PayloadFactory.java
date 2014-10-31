package com.utoronto.timemng.handler;


import com.utoronto.timemng.descriptor.Month;

/**
 * Date: 2014-10-26.
 */
public class PayloadFactory {

    public PayloadHandler create(final String payload) {
        // TODO: 1. De-serialize payload into a POJO class "PayloadDescriptor.class"

        // TODO: provide different handlers based on the content of the payload...
        Month month = new Month();
        return new CreateEventPayloadHandler(payload, month);
    }
}
