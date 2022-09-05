package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Branch_Model {

    int success;
    String message;
    ArrayList<Branch> product;

    String  branch_long,branch_lat,lang,user_lat,user_long;

    public String getUser_lat() {
        return user_lat;
    }

    public void setUser_lat(String user_lat) {
        this.user_lat = user_lat;
    }

    public String getUser_long() {
        return user_long;
    }

    public void setUser_long(String user_long) {
        this.user_long = user_long;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getBranch_long() {
        return branch_long;
    }

    public void setBranch_long(String branch_long) {
        this.branch_long = branch_long;
    }

    public String getBranch_lat() {
        return branch_lat;
    }

    public void setBranch_lat(String branch_lat) {
        this.branch_lat = branch_lat;
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

    public ArrayList<Branch> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Branch> product) {
        this.product = product;
    }

    public static class Branch{
        String branch_id,phone, name, address, branch_long,branch_lat, distance,user_lat,user_long;
        int check=0;

        public Branch(String branch_id, String name) {
            this.branch_id = branch_id;
            this.name = name;
        }

        public String getUser_lat() {
            return user_lat;
        }

        public void setUser_lat(String user_lat) {
            this.user_lat = user_lat;
        }

        public String getUser_long() {
            return user_long;
        }

        public void setUser_long(String user_long) {
            this.user_long = user_long;
        }

        public int getCheck() {
            return check;
        }

        public void setCheck(int check) {
            this.check = check;
        }

        public String getBranch_id() {
            return branch_id;
        }

        public void setBranch_id(String branch_id) {
            this.branch_id = branch_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getBranch_long() {
            return branch_long;
        }

        public void setBranch_long(String branch_long) {
            this.branch_long = branch_long;
        }

        public String getBranch_lat() {
            return branch_lat;
        }

        public void setBranch_lat(String branch_lat) {
            this.branch_lat = branch_lat;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }


}
