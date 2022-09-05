package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Reviews_Model {
    int success;
    String message;
    ArrayList<Review> product;



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

    public ArrayList<Review> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Review> product) {
        this.product = product;
    }
}
