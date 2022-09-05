package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Prices_Model {
    int success;
    String message;
    ArrayList<Price> product;

    public Prices_Model() {
    }

    public Prices_Model(int success, String message) {
        this.success = success;
        this.message = message;
    }

    public Prices_Model(int success, ArrayList<Price> product) {
        this.success = success;
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

    public ArrayList<Price> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Price> product) {
        this.product = product;
    }

    public class Price {
     String   sub_category_size_price_id,sub_category_size_name,sub_category_size_price,sub_category_id,discount,discount_value,sub_category_size_price_after_disc;

     int check=0;

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDiscount_value() {
            return discount_value;
        }

        public void setDiscount_value(String discount_value) {
            this.discount_value = discount_value;
        }

        public String getSub_category_size_price_after_disc() {
            return sub_category_size_price_after_disc;
        }

        public void setSub_category_size_price_after_disc(String sub_category_size_price_after_disc) {
            this.sub_category_size_price_after_disc = sub_category_size_price_after_disc;
        }

        public int getCheck() {
            return check;
        }

        public void setCheck(int check) {
            this.check = check;
        }

        public Price() {
        }

        public String getSub_category_size_price_id() {
            return sub_category_size_price_id;
        }

        public void setSub_category_size_price_id(String sub_category_size_price_id) {
            this.sub_category_size_price_id = sub_category_size_price_id;
        }

        public String getSub_category_size_name() {
            return sub_category_size_name;
        }

        public void setSub_category_size_name(String sub_category_size_name) {
            this.sub_category_size_name = sub_category_size_name;
        }

        public String getSub_category_size_price() {
            return sub_category_size_price;
        }

        public void setSub_category_size_price(String sub_category_size_price) {
            this.sub_category_size_price = sub_category_size_price;
        }

        public String getSub_category_id() {
            return sub_category_id;
        }

        public void setSub_category_id(String sub_category_id) {
            this.sub_category_id = sub_category_id;
        }
    }

}
