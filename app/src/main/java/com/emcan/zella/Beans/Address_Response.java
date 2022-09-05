package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Address_Response {
    ArrayList<Address> product;
    String message;
    int success;

    public ArrayList<Address> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Address> product) {
        this.product = product;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}
