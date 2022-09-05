package com.emcan.zella.Beans;

import android.os.Parcel;
import android.os.Parcelable;


public class Review  implements Parcelable {
    String comment_id,client_id,sub_category_id,comment,client_name;
    int rate;


    protected Review(Parcel in) {
        comment_id = in.readString();
        client_id = in.readString();
        sub_category_id = in.readString();
        comment = in.readString();
        client_name = in.readString();
        rate=in.readInt();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public int getRate() {
        return rate;
    }

    public void setRate( int rate) {
        this.rate = rate;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(String sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(comment_id);
        parcel.writeString(client_id);
        parcel.writeString(sub_category_id);
        parcel.writeString(comment);
        parcel.writeString(client_name);
        parcel.writeInt(rate);
    }
}
