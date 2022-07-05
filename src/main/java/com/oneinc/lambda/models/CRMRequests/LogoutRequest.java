package com.oneinc.lambda.models.CRMRequests;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LogoutRequest {
    private final String session;
}
