
package com.emcan.zella.Beans;

import com.google.gson.annotations.SerializedName;


public class LoyaltyPointsResponse {

    @SerializedName("client_points")
    private String mClientPoints;
    @SerializedName("client_points_show_pop")
    private String mClientPointsShowPop;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("points_text")
    private String mPointsText;
    @SerializedName("success")
    private Long mSuccess;
    private String points_status;
    private String summary,check_discount,vat,total_amount_after_disc,vat_value,charge,discount_value,
            total_amount;
    private double discount_percentage;

    public String getmClientPoints() {
        return mClientPoints;
    }

    public void setmClientPoints(String mClientPoints) {
        this.mClientPoints = mClientPoints;
    }

    public String getCheck_discount() {
        return check_discount;
    }

    public void setCheck_discount(String check_discount) {
        this.check_discount = check_discount;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getTotal_amount_after_disc() {
        return total_amount_after_disc;
    }

    public void setTotal_amount_after_disc(String total_amount_after_disc) {
        this.total_amount_after_disc = total_amount_after_disc;
    }

    public String getVat_value() {
        return vat_value;
    }

    public void setVat_value(String vat_value) {
        this.vat_value = vat_value;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getDiscount_value() {
        return discount_value;
    }

    public void setDiscount_value(String discount_value) {
        this.discount_value = discount_value;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public double getDiscount_percentage() {
        return discount_percentage;
    }

    public void setDiscount_percentage(double discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    public String getPoints_status() {
        return points_status;
    }

    public void setPoints_status(String points_status) {
        this.points_status = points_status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getClientPoints() {
        return mClientPoints;
    }

    public void setClientPoints(String clientPoints) {
        mClientPoints = clientPoints;
    }

    public String getClientPointsShowPop() {
        return mClientPointsShowPop;
    }

    public void setClientPointsShowPop(String clientPointsShowPop) {
        mClientPointsShowPop = clientPointsShowPop;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getPointsText() {
        return mPointsText;
    }

    public void setPointsText(String pointsText) {
        mPointsText = pointsText;
    }

    public Long getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Long success) {
        mSuccess = success;
    }

}
