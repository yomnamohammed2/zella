package com.emcan.zella.Beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Complain_response {

    ArrayList<Complain> product;
    int success;
    String message;

    public ArrayList<Complain> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Complain> product) {
        this.product = product;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Complain implements Serializable,Parcelable{

        String complaint_id,content,title,date,client_id,message_type_id;
        ArrayList <Messages_model> messages;

        public Complain(Parcel in) {
            complaint_id = in.readString();
            content = in.readString();
            title = in.readString();
            date = in.readString();
            client_id=in.readString();
            message_type_id=in.readString();
        }

        public Complain() {

        }

        public String getClient_id() {
            return client_id;
        }

        public void setClient_id(String client_id) {
            this.client_id = client_id;
        }

        public String getMessage_type_id() {
            return message_type_id;
        }

        public void setMessage_type_id(String message_type_id) {
            this.message_type_id = message_type_id;
        }

        public static final Creator<Complain> CREATOR = new Creator<Complain>() {
            @Override
            public Complain createFromParcel(Parcel in) {
                return new Complain(in);
            }

            @Override
            public Complain[] newArray(int size) {
                return new Complain[size];
            }
        };

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getComplaint_id() {
            return complaint_id;
        }

        public void setComplaint_id(String complaint_id) {
            this.complaint_id = complaint_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public ArrayList<Messages_model> getMessages() {
            return messages;
        }

        public void setMessages(ArrayList<Messages_model> messages) {
            this.messages = messages;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(complaint_id);
            parcel.writeString(content);
            parcel.writeString(title);
            parcel.writeString(date);
            parcel.writeString(client_id);
            parcel.writeString(message_type_id);
        }
    }

    public static class Messages_model{
        String content,is_read,date,type,title;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIs_read() {
            return is_read;
        }

        public void setIs_read(String is_read) {
            this.is_read = is_read;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
