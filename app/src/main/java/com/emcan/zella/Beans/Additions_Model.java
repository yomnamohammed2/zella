package com.emcan.zella.Beans;

import java.io.Serializable;
import java.util.ArrayList;

public class Additions_Model {

    int success;
    String message;
    ArrayList<Addition> product;

    public Additions_Model(int success, String message) {
        this.success = success;
        this.message = message;
    }

    public Additions_Model() {
    }

    public Additions_Model(int success, ArrayList<Addition> product) {

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

    public ArrayList<Addition> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Addition> product) {
        this.product = product;
    }

    public class Addition implements Serializable{


       String addition_id,addition_name,addition_price;

       int check=0;

        public int getCheck() {
            return check;
        }

        public void setCheck(int check) {
            this.check = check;
        }

        public Addition() {
        }

        public String getAddition_id() {
            return addition_id;
        }

        public void setAddition_id(String addition_id) {
            this.addition_id = addition_id;
        }

        public String getAddition_name() {
            return addition_name;
        }

        public void setAddition_name(String addition_name) {
            this.addition_name = addition_name;
        }

        public String getAddition_price() {
            return addition_price;
        }

        public void setAddition_price(String addition_price) {
            this.addition_price = addition_price;
        }
    }
}
