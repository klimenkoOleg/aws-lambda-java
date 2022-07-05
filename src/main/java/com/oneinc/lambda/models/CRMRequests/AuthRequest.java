package com.oneinc.lambda.models.CRMRequests;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthRequest {
    @SerializedName("user_name")
    private final String userName;
    private final String password;
//    private String version = "1";
}
