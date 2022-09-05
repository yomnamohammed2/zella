package com.emcan.zella.Beans;


import java.util.ArrayList;

public class LoginResponse {
    int success;
    String message;
    ArrayList<User> product;

    public LoginResponse(int success, ArrayList<User> product) {
        this.success = success;
        this.product = product;
    }

    public LoginResponse(int success, String message) {
        this.success = success;
        this.message = message;
    }

    public LoginResponse() {
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

    public ArrayList<User> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<User> product) {
        this.product = product;
    }
}

