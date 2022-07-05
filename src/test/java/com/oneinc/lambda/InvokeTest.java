package com.oneinc.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

class InvokeTest {
    private static final Logger logger = LoggerFactory.getLogger(InvokeTest.class);

    @Test
    void invokeTest() {
        logger.info("Invoke TEST");
        APIGatewayProxyRequestEvent requestEvent = new APIGatewayProxyRequestEvent();
        requestEvent.setHttpMethod("POST");
//    requestEvent.setBody();
//    requestEvent.

//    HashMap<String,String> event = new HashMap<String,String>();
        Context context = new TestContext();
        Handler handler = new Handler();
        APIGatewayProxyResponseEvent responseEvent = handler.handleRequest(requestEvent, context);
        assertTrue(responseEvent.getStatusCode().equals(200));
    }

}
