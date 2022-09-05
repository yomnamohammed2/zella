package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Avaliable_Model {

    ArrayList <Available> product;
    int success;
    String message,not_exist;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNot_exist() {
        return not_exist;
    }

    public void setNot_exist(String not_exist) {
        this.not_exist = not_exist;
    }

    public ArrayList<Available> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Available> product) {
        this.product = product;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public class Available
    {
       String accept_orders,discount,discount_percentage;

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDiscount_percentage() {
            return discount_percentage;
        }

        public void setDiscount_percentage(String discount_percentage) {
            this.discount_percentage = discount_percentage;
        }

        public String getAccept_orders() {
            return accept_orders;
        }

        public void setAccept_orders(String accept_orders) {
            this.accept_orders = accept_orders;
        }
    }}
