
package com.emcan.zella.Beans;

import com.google.gson.annotations.SerializedName;


public class TapPaymentResponse {

    @SerializedName("success")
    private Long mSuccess;
    @SerializedName("url")
    private String mUrl;

    public Long getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Long success) {
        mSuccess = success;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

}
