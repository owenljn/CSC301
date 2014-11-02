package com.utoronto.timemng.handler;


import com.utoronto.timemng.descriptor.*;
import com.utoronto.timemng.descriptor.type.PayloadAction;
import com.utoronto.timemng.descriptor.type.WhichDay;
import com.utoronto.timemng.descriptor.type.WhichMonth;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Date: 2014-10-26.
 */
public class PayloadFactory {

    public PayloadHandler create(final String payload) {
        // TODO: 1. De-serialize payload into a POJO class "PayloadDescriptor.class"

        // TODO: provide different handlers based on the content of the payload...
        // TODO: Remove, here for testing purposes.
        int value = 1;
        final PayloadAction payloadAction = PayloadAction.lookup(value);
        Event event = new Event("TestEvent", 5, 30, 6, 30, null, false, null);
        Collection<Event> eventList = new ArrayList<Event>();
        eventList.add(event);
        switch (payloadAction) {
            case CREATE_EVENT:
                return new CreateEventPayloadHandler(payload, eventList);
            case DELETE_EVENT:
                return new DeleteEventPayloadHandler();
            case UPDATE_EVENT:
                return new UpdateEventPayloadHandler();
            default:
                throw new UnsupportedOperationException("Type ["+payloadAction+"] is not supported");
        }
    }
}
