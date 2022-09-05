package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Order_respose {

    ArrayList<Order_model> product;
    int success;
    String message;

    public ArrayList<Order_model> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Order_model> product) {
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

    public static class Order_model {

        String order_id,payment, deliver_id,client_id,client_address_id ,cart_id,branch_id,lang,session_id,mobile_type,country_id,
                use_points,mobile_version,discount_code,is_paid;

        public String getIs_paid() {
            return is_paid;
        }

        public void setIs_paid(String is_paid) {
            this.is_paid = is_paid;
        }

        public String getDiscount_code() {
            return discount_code;
        }

        public void setDiscount_code(String discount_code) {
            this.discount_code = discount_code;
        }

        public String getMobile_version() {
            return mobile_version;
        }

        public void setMobile_version(String mobile_version) {
            this.mobile_version = mobile_version;
        }

        public String getUse_points() {
            return use_points;
        }

        public void setUse_points(String use_points) {
            this.use_points = use_points;
        }

        public String getCountry_id() {
            return country_id;
        }

        public void setCountry_id(String country_id) {
            this.country_id = country_id;
        }

        public String getDevice_type() {
            return mobile_type;
        }

        public void setDevice_type(String mobile_type) {
            this.mobile_type =mobile_type;
        }

        public String getSession_id() {
            return session_id;
        }

        public void setSession_id(String session_id) {
            this.session_id = session_id;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getBranch_id() {
            return branch_id;
        }

        public void setBranch_id(String branch_id) {
            this.branch_id = branch_id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getPayment() {
            return payment;
        }

        public void setPayment(String payment) {
            this.payment = payment;
        }

        public String getDeliver_id() {
            return deliver_id;
        }

        public void setDeliver_id(String deliver_id) {
            this.deliver_id = deliver_id;
        }

        public String getClient_id() {
            return client_id;
        }

        public void setClient_id(String client_id) {
            this.client_id = client_id;
        }

        public String getClient_address_id() {
            return client_address_id;
        }

        public void setClient_address_id(String client_address_id) {
            this.client_address_id = client_address_id;
        }

        public String getCart_id() {
            return cart_id;
        }

        public void setCart_id(String cart_id) {
            this.cart_id = cart_id;
        }
    }
}