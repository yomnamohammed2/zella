
package com.emcan.zella.Beans;

import com.google.gson.annotations.SerializedName;


public class DriverData {

    @SerializedName("branch_id")
    private String mBranchId;
    @SerializedName("date_added")
    private String mDateAdded;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("id")
    private String mId;
    @SerializedName("lat_loca")
    private Object mLatLoca;
    @SerializedName("long_loca")
    private Object mLongLoca;
    @SerializedName("name")
    private String mName;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("phone")
    private String mPhone;

    public String getBranchId() {
        return mBranchId;
    }

    public void setBranchId(String branchId) {
        mBranchId = branchId;
    }

    public String getDateAdded() {
        return mDateAdded;
    }

    public void setDateAdded(String dateAdded) {
        mDateAdded = dateAdded;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Object getLatLoca() {
        return mLatLoca;
    }

    public void setLatLoca(Object latLoca) {
        mLatLoca = latLoca;
    }

    public Object getLongLoca() {
        return mLongLoca;
    }

    public void setLongLoca(Object longLoca) {
        mLongLoca = longLoca;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

}
