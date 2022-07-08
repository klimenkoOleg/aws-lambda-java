package com.oneinc.lambda.utils;

import com.oneinc.lambda.models.CRMRequests.ApiRequest;
import lombok.experimental.UtilityClass;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class NetworkUtils {

    public String invokeHttpCall(ApiRequest apiRequest, String url) throws IOException {
        String restData = "method="+apiRequest.getMethod()+"&input_type=JSON&response_type=JSON&rest_data="+apiRequest.getRest_data();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, restData);
        Request.Builder builder = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded");
        if(!apiRequest.getMethod().equalsIgnoreCase("login")) {
            builder.addHeader("Cookie", "PHPSESSID="+apiRequest.getSessionId()+"");
        }
        Request request = builder.build();
        Response response = client.newCall(request).execute();
        assert response.body() != null;
        return response.body().string();
    }
}
