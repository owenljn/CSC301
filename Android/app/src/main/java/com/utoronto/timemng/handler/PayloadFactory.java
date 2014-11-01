package com.utoronto.timemng.handler;


import com.utoronto.timemng.descriptor.Year;
import com.utoronto.timemng.descriptor.Month;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Date: 2014-10-26.
 */
public class PayloadFactory {

    public PayloadHandler create(final String payload) {
        // TODO: 1. De-serialize payload into a POJO class "PayloadDescriptor.class"

        // TODO: provide different handlers based on the content of the payload...
        Collection<Month> monthList = new ArrayList<Month>();
        Month month = new Month();
        monthList.add(month);
        Year year = new Year(2014, monthList);
        return new CreateEventPayloadHandler(payload, year);
    }
}
