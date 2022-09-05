
package com.emcan.zella.Beans;

import com.google.gson.annotations.SerializedName;

public class TapSuccessResponse {

    @SerializedName("amount")
    private Double mAmount;
    @SerializedName("payment_method")
    private String mPaymentMethod;
    @SerializedName("payment_type")
    private String mPaymentType;
    @SerializedName("source_id")
    private String mSourceId;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("success")
    private Long mSuccess;

    public Double getAmount() {
        return mAmount;
    }

    public void setAmount(Double amount) {
        mAmount = amount;
    }

    public String getPaymentMethod() {
        return mPaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        mPaymentMethod = paymentMethod;
    }

    public String getPaymentType() {
        return mPaymentType;
    }

    public void setPaymentType(String paymentType) {
        mPaymentType = paymentType;
    }

    public String getSourceId() {
        return mSourceId;
    }

    public void setSourceId(String sourceId) {
        mSourceId = sourceId;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public Long getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Long success) {
        mSuccess = success;
    }

}
