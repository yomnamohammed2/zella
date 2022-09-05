package com.emcan.zella.Beans;

import java.io.Serializable;
import java.util.ArrayList;

public class Remove_Response implements Serializable {

    ArrayList <Removes> product;
    int success;

    public ArrayList<Removes> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Removes> product) {
        this.product = product;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public  class Removes implements Serializable{
        String remove_id,remove_title,remove_name;

        int check=0;

        public String getRemove_name() {
            return remove_name;
        }

        public void setRemove_name(String remove_name) {
            this.remove_name = remove_name;
        }

        public int getCheck() {
            return check;
        }

        public void setCheck(int check) {
            this.check = check;
        }

        public String getRemove_id() {
            return remove_id;
        }

        public void setRemove_id(String remove_id) {
            this.remove_id = remove_id;
        }

        public String getRemove_title() {
            return remove_title;
        }

        public void setRemove_title(String remove_title) {
            this.remove_title = remove_title;
        }
    }
}
