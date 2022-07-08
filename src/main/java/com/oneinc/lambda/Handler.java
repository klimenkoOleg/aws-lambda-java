package com.oneinc.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oneinc.lambda.config.ParamsConfig;
import com.oneinc.lambda.exceptions.IllegalParametersException;
import com.oneinc.lambda.service.SugarCrmService;
import com.oneinc.lambda.utils.RequestResponseUtils;

import static com.oneinc.lambda.utils.LogUtils.logEnvironment;
import static org.apache.commons.lang3.StringUtils.isEmpty;


/**
 * Handler for API Gateway and ALB.
 * Types APIGatewayProxyRequestEvent and APIGatewayProxyResponseEvent allow this handler to
 * be invoked from AWS Gateway and ALB. It also allows to check health of lambda from enclosing
 * Target group.
 *
 * @author oklimenko@gmail.com
 */
public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static String paramMethod = "method";
    private static String paramRestData = "rest_data";

    private final Gson gson;
    private final ParamsConfig paramsConfig;
    private final SugarCrmService sugarCrmService;

    public Handler() {
        paramsConfig = new ParamsConfig();
        gson = new GsonBuilder().create();
        sugarCrmService = new SugarCrmService(paramsConfig, gson);
    }

    public Handler(ParamsConfig paramsConfig, Gson gson, SugarCrmService sugarCrmService) {
        this.paramsConfig = paramsConfig;
        this.gson = gson;
        this.sugarCrmService = sugarCrmService;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        // log execution details
        logEnvironment(event, context, gson);
        String method = RequestResponseUtils.getParameterFromBody(event, paramMethod);
        String restData = RequestResponseUtils.getParameterFromBody(event, paramRestData);

        checkParamsValidity(event, method);

        String crmResponse = sugarCrmService.doCrmApiCall(method, restData, paramsConfig.getUrl());
        return RequestResponseUtils.generateSuccessJsonResponse(crmResponse);
    }

    
    private void checkParamsValidity(APIGatewayProxyRequestEvent event, String method) {
        if (!"POST".equals(event.getHttpMethod())) {
            throw new IllegalParametersException("Expected POST method. Actual method: " + event.getHttpMethod());
        }
        if (isEmpty(method)) {
            throw new IllegalParametersException("Missing parameter: " + paramMethod);
        }
        if (isEmpty(method)) {
            throw new IllegalParametersException("Missing parameter: " + paramRestData);
        }
    }

}
