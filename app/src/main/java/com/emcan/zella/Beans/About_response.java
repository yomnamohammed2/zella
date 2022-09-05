package com.emcan.zella.Beans;

import java.util.ArrayList;

public class About_response {

    int success;
    ArrayList<Sofra> product;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public ArrayList<Sofra> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Sofra> product) {
        this.product = product;
    }

    public class Sofra{

        String image;
        String content;

        String id ,text,date,is_read;


        String message_type_id,title;

        public String getIs_read() {
            return is_read;
        }

        public void setIs_read(String is_read) {
            this.is_read = is_read;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage_type_id() {
            return message_type_id;
        }

        public void setMessage_type_id(String message_type_id) {
            this.message_type_id = message_type_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}
