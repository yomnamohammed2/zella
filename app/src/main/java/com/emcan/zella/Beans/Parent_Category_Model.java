package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Parent_Category_Model {

        int success;
        String message;
        ArrayList<Parent_Category> product;

        public Parent_Category_Model(int success, String message) {
            this.success = success;
            this.message = message;
        }

        public Parent_Category_Model(int success, ArrayList<Parent_Category> product) {
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

        public ArrayList<Parent_Category> getProduct() {
            return product;
        }

        public void setProduct(ArrayList<Parent_Category> product) {
            this.product = product;
        }


    }

