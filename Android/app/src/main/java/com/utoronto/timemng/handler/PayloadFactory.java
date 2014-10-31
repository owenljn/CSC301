package com.utoronto.timemng.handler;


/**
 * Date: 2014-10-26.
 */
public class PayloadFactory {

    public PayloadHandler create(final String payload) {
        // TODO: 1. De-serialize payload into a POJO class "PayloadDescriptor.class"
    	//if (payload[0] == 'C'){
    	return new CreateTaskPayloadHandler(payload);
   /* 	//}else if (payload[0] == 'U'){
         //   return new UpdateTaskPayloadHandler(payload);
    	}else if (payload[0] == 'D'){
            return new DeleteTaskPayloadHandler(payload);
    	}else{
    		return null;
    	}
*/
        // TODO: provide different handlers based on the content of the payload...

    }
}
