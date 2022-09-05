package com.emcan.zella.Beans;


import java.util.ArrayList;

public class PaymentResponse {

    private int success;
    private ArrayList<Paymentt> product;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public ArrayList<Paymentt> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Paymentt> product) {
        this.product = product;
    }
}
