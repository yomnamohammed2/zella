package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Payment_result {

    ArrayList<Result_Model> product;
    int success;

    public ArrayList<Result_Model> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Result_Model> product) {
        this.product = product;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public class Result_Model {
        String result;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
}
