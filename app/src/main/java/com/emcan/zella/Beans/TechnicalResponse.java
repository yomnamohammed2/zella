package com.emcan.zella.Beans;

import java.util.ArrayList;

/**
 * Created by HP on 2019-09-24.
 */

public class TechnicalResponse {
    ArrayList<Technical> product;
    int success;

    public ArrayList<Technical> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Technical> product) {
        this.product = product;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}
