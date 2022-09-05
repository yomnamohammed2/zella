package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Staff_Response {
    int success;
    ArrayList<Staff_member> product;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public ArrayList<Staff_member> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Staff_member> product) {
        this.product = product;
    }
    public class Staff_member{

        String name,position,image,id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
