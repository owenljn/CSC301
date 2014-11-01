package com.utoronto.timemng.handler;


import com.utoronto.timemng.descriptor.Year;
import com.utoronto.timemng.descriptor.Month;
import com.utoronto.timemng.descriptor.type.PayloadAction;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Date: 2014-10-26.
 */
public class PayloadFactory {

    public PayloadHandler create(final String payload) {
        // TODO: 1. De-serialize payload into a POJO class "PayloadDescriptor.class"

        // TODO: provide different handlers based on the content of the payload...
        int value = 1;
        final PayloadAction payloadAction = PayloadAction.lookup(value);
//        Collection<Month> monthList = new ArrayList<Month>();
//        Month month = new Month();
//        monthList.add(month);
//        Year year = new Year(2014, monthList);
//        return new CreateEventPayloadHandler(payload, year);
        switch (payloadAction) {
            case CREATE_EVENT:
                return null;
//                return new CreateEventPayloadHandler(payload, year);
            case DELETE_EVENT:
                return new DeleteEventPayloadHandler();
            case UPDATE_EVENT:
                return new UpdateEventPayloadHandler();
            default:
                throw new UnsupportedOperationException("Type ["+payloadAction+"] is not supported");
        }
    }
}
