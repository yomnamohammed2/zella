package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Verification_Model {
    ArrayList<Verification_code> product;
    int success;
    String message;

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

    public ArrayList<Verification_code> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Verification_code> product) {
        this.product = product;
    }

    public class  Verification_code{

        int verify_key;
        String res;

        public String getRes() {
            return res;
        }

        public void setRes(String res) {
            this.res = res;
        }

        public int getVerify_key() {
            return verify_key;
        }

        public void setVerify_key(int verify_key) {
            this.verify_key = verify_key;
        }
    }
}
