package com.oneinc.lambda.service;


import com.google.gson.Gson;
import com.oneinc.lambda.config.ParamsConfig;
import com.oneinc.lambda.models.CRMRequests.ApiRequest;
import com.oneinc.lambda.models.CRMRequests.AuthRequest;
import com.oneinc.lambda.models.CRMRequests.CrmRequestBody;
import com.oneinc.lambda.models.CRMRequests.LogoutRequest;
import com.oneinc.lambda.models.CRMRequests.SessionRequest;
import com.oneinc.lambda.models.CRMResponses.SessionResponse;
import com.oneinc.lambda.utils.NetworkUtils;
import com.oneinc.lambda.utils.RequestResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;

@RequiredArgsConstructor
public class SugarCrmService {

    private final ParamsConfig paramsConfig;
    private final Gson gson;

    private String getSessionID() throws IOException {
        AuthRequest authRequest = new AuthRequest(paramsConfig.getUserName(), paramsConfig.getPassword());
        ApiRequest request = ApiRequest.builder()
//                .url(paramsConfig.getUrl())
                .rest_data(gson.toJson(new SessionRequest(authRequest)))
                .method("Login")
                .build();
        String sessionResponseData = NetworkUtils.invokeHttpCall(request, paramsConfig.getUrl());
        return gson
                .fromJson(sessionResponseData, SessionResponse.class)
                .getId();
    }

    private void logout(String session, String url) throws IOException {
        LogoutRequest logoutRequest = new LogoutRequest(session);
        ApiRequest apiRequest = ApiRequest.builder()
                .rest_data(new Gson().toJson(logoutRequest))
                .method("logout")
                .build();
        NetworkUtils.invokeHttpCall(apiRequest, url);
    }

    @SneakyThrows
    public String doCrmApiCall(String method, String wrappedRestData, String url) {
        String sessionId = getSessionID();
        String str_json = RequestResponseUtils.extractRestData(gson, wrappedRestData);

        CrmRequestBody crmRequestBody = CrmRequestBody.builder()
                .session(sessionId)
                .str_json(str_json)
                .build();
        String requestData = gson.toJson(crmRequestBody);

        ApiRequest request = ApiRequest
                .builder()
                .rest_data(requestData)
                .method(method)
                .sessionId(sessionId)
                .build();
        String crmResponse = NetworkUtils.invokeHttpCall(request, url);
        logout(sessionId, url);
        return crmResponse;
    }
}
