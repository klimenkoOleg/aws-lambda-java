package com.oneinc.lambda.models.CRMRequests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class SessionRequest {
    private final AuthRequest user_auth;
}
