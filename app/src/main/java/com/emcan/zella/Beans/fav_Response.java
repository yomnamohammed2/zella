package com.emcan.zella.Beans;

import java.util.ArrayList;

public class fav_Response {

    int success;
    String message;
    ArrayList<Sub_Category> product;

    public fav_Response() {
    }

    public fav_Response(int success, String message) {
        this.success = success;
        this.message = message;
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

    public ArrayList<Sub_Category> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Sub_Category> product) {
        this.product = product;
    }

    public fav_Response(int success, String message, ArrayList<Sub_Category> product) {

        this.success = success;
        this.message = message;
        this.product = product;
    }

    public class Fav{
       String fav_id;
        String client_id;
        String sub_category_id;
        String sub_category_name;
        String sub_category_image,type,spicy_type,spicy_show;
        String sub_category_desc;



        public String getSub_category_desc() {
            return sub_category_desc;
        }

        public void setSub_category_desc(String sub_category_desc) {
            this.sub_category_desc = sub_category_desc;
        }



        public String getFav_id() {
            return fav_id;
        }

        public void setFav_id(String fav_id) {
            this.fav_id = fav_id;
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

        public String getSub_category_name() {
            return sub_category_name;
        }

        public void setSub_category_name(String sub_category_name) {
            this.sub_category_name = sub_category_name;
        }

        public String getSub_category_image() {
            return sub_category_image;
        }

        public void setSub_category_image(String sub_category_image) {
            this.sub_category_image = sub_category_image;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSpicy_type() {
            return spicy_type;
        }

        public void setSpicy_type(String spicy_type) {
            this.spicy_type = spicy_type;
        }

        public String getSpicy_show() {
            return spicy_show;
        }

        public void setSpicy_show(String spicy_show) {
            this.spicy_show = spicy_show;
        }
    }
}

