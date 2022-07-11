package com.oneinc.lambda.config;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * This config should be feed by parameters stored externally.
 * Sensitive parameters like connection strings and passwords should be stored in vault-like storage.
 *
 * @author oklimenko@gmail.com
 */
@Getter
public class ParamsConfig {

    @SerializedName("user_name")
    private String userName;
    private String password;
    private String url;

    public ParamsConfig() {
        // TODO read from secured params storage
        userName = "gbartlett";
        password = "2b84d0034804a0e95079dc92483f769f";
        url = "https://servicesqa.insurpay.com:9443/ip_dev/custom/service/v4_1_custom/rest.php";
    }
}
