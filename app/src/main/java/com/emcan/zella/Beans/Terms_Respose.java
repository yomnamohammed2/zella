package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Terms_Respose {

    ArrayList<Terms_Model> product;
    int success;
    String message;

    public ArrayList<Terms_Model> getProduct() {
        return product;
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

    public void setProduct(ArrayList<Terms_Model> product) {
        this.product = product;
    }

    public class Terms_Model{
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
