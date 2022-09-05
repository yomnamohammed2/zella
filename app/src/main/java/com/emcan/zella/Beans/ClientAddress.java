
package com.emcan.zella.Beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ClientAddress implements Serializable {

    @SerializedName("additional_code")
    private String mAdditionalCode;
    @SerializedName("block")
    private String mBlock;
    @SerializedName("building")
    private String mBuilding;
    @SerializedName("client_address_id")
    private String mClientAddressId;
    @SerializedName("client_id")
    private String mClientId;
    @SerializedName("client_phone")
    private String mClientPhone;
    @SerializedName("country_id")
    private String mCountryId;
    @SerializedName("date")
    private String mDate;
    @SerializedName("flat_number")
    private String mFlatNumber;
    @SerializedName("lang")
    private String mLang;
    @SerializedName("lat")
    private String mLat;
    @SerializedName("note")
    private String mNote;
    @SerializedName("postal_code")
    private String mPostalCode;
    @SerializedName("region")
    private String mRegion;
    @SerializedName("region_name")
    private String mRegionName;
    @SerializedName("road")
    private String mRoad;

    public String getAdditionalCode() {
        return mAdditionalCode;
    }

    public void setAdditionalCode(String additionalCode) {
        mAdditionalCode = additionalCode;
    }

    public String getBlock() {
        return mBlock;
    }

    public void setBlock(String block) {
        mBlock = block;
    }

    public String getBuilding() {
        return mBuilding;
    }

    public void setBuilding(String building) {
        mBuilding = building;
    }

    public String getClientAddressId() {
        return mClientAddressId;
    }

    public void setClientAddressId(String clientAddressId) {
        mClientAddressId = clientAddressId;
    }

    public String getClientId() {
        return mClientId;
    }

    public void setClientId(String clientId) {
        mClientId = clientId;
    }

    public String getClientPhone() {
        return mClientPhone;
    }

    public void setClientPhone(String clientPhone) {
        mClientPhone = clientPhone;
    }

    public String getCountryId() {
        return mCountryId;
    }

    public void setCountryId(String countryId) {
        mCountryId = countryId;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getFlatNumber() {
        return mFlatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        mFlatNumber = flatNumber;
    }

    public String getLang() {
        return mLang;
    }

    public void setLang(String lang) {
        mLang = lang;
    }

    public String getLat() {
        return mLat;
    }

    public void setLat(String lat) {
        mLat = lat;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }

    public String getPostalCode() {
        return mPostalCode;
    }

    public void setPostalCode(String postalCode) {
        mPostalCode = postalCode;
    }

    public String getRegion() {
        return mRegion;
    }

    public void setRegion(String region) {
        mRegion = region;
    }

    public String getRegionName() {
        return mRegionName;
    }

    public void setRegionName(String regionName) {
        mRegionName = regionName;
    }

    public String getRoad() {
        return mRoad;
    }

    public void setRoad(String road) {
        mRoad = road;
    }

}
