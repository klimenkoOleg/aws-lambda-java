package com.oneinc.lambda.models.CRMRequests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CrmRequestBody {
    private String session;
    private String str_json;
}
