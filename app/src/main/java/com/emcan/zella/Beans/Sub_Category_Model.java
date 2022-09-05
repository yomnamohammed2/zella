package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Sub_Category_Model {
    int success;
    String message;
    ArrayList<Sub_Category> product;

    public Sub_Category_Model(int success, String message) {
        this.success = success;
        this.message = message;
    }

    public Sub_Category_Model(int success, ArrayList<Sub_Category> product) {
        this.success = success;
        this.product = product;
    }

    public Sub_Category_Model() {
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
}
