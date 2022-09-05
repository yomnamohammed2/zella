package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Check_client {

    ArrayList <Check_response> product;
    int success;

    public ArrayList<Check_response> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Check_response> product) {
        this.product = product;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public  class  Check_response{

        int exist;

        public int getExist() {
            return exist;
        }

        public void setExist(int exist) {
            this.exist = exist;
        }
    }
}
