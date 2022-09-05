
package com.emcan.zella.Beans;

import com.google.gson.annotations.SerializedName;

public class DiscountResponse {

    @SerializedName("check_discount")
    private String mCheckDiscount;
    @SerializedName("discount_code_percentage")
    private String mDiscountCodePercentage;
    @SerializedName("discount_percentage")
    private Long mDiscountPercentage;
    @SerializedName("discount_value")
    private String mDiscountValue;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("success")
    private Long mSuccess;
    @SerializedName("summary")
    private String mSummary;
    @SerializedName("total_amount")
    private String mTotalAmount;
    @SerializedName("total_amount_after_disc")
    private String mTotalAmountAfterDisc;
    @SerializedName("vat")
    private String mVat;
    @SerializedName("vat_value")
    private String mVatValue;
    private String charge;

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getCheckDiscount() {
        return mCheckDiscount;
    }

    public void setCheckDiscount(String checkDiscount) {
        mCheckDiscount = checkDiscount;
    }

    public String getDiscountCodePercentage() {
        return mDiscountCodePercentage;
    }

    public void setDiscountCodePercentage(String discountCodePercentage) {
        mDiscountCodePercentage = discountCodePercentage;
    }

    public Long getDiscountPercentage() {
        return mDiscountPercentage;
    }

    public void setDiscountPercentage(Long discountPercentage) {
        mDiscountPercentage = discountPercentage;
    }

    public String getDiscountValue() {
        return mDiscountValue;
    }

    public void setDiscountValue(String discountValue) {
        mDiscountValue = discountValue;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Long success) {
        mSuccess = success;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getTotalAmount() {
        return mTotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        mTotalAmount = totalAmount;
    }

    public String getTotalAmountAfterDisc() {
        return mTotalAmountAfterDisc;
    }

    public void setTotalAmountAfterDisc(String totalAmountAfterDisc) {
        mTotalAmountAfterDisc = totalAmountAfterDisc;
    }

    public String getVat() {
        return mVat;
    }

    public void setVat(String vat) {
        mVat = vat;
    }

    public String getVatValue() {
        return mVatValue;
    }

    public void setVatValue(String vatValue) {
        mVatValue = vatValue;
    }

}
