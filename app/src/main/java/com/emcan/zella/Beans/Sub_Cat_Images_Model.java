package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Sub_Cat_Images_Model {
    int success;
    String message;
    ArrayList<Images> product;

    public Sub_Cat_Images_Model(int success, String message, ArrayList<Images> product) {
        this.success = success;
        this.message = message;
        this.product = product;
    }

    public Sub_Cat_Images_Model(int success, ArrayList<Images> product) {
        this.success = success;
        this.product = product;
    }

    public Sub_Cat_Images_Model(int success, String message) {
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

    public ArrayList<Images> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Images> product) {
        this.product = product;
    }

    public Sub_Cat_Images_Model() {

    }

    public class Images{

       String sub_category_id,sub_category_image;


        public String getSub_category_id() {
            return sub_category_id;
        }

        public void setSub_category_id(String sub_category_id) {
            this.sub_category_id = sub_category_id;
        }

        public String getSub_category_image() {
            return sub_category_image;
        }

        public void setSub_category_image(String sub_category_image) {
            this.sub_category_image = sub_category_image;
        }
    }
}
