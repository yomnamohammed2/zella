package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Menu_Response {
    int success;
    ArrayList<Menu_Images> product;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public ArrayList<Menu_Images> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Menu_Images> product) {
        this.product = product;
    }
    public class Menu_Images{
        String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
