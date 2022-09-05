package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Contact_Response {

    int success;
    ArrayList<Contact> product;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public ArrayList<Contact> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Contact> product) {
        this.product = product;
    }

    public  class Contact{
        String address,phone,mobile,email,instagram,twitter,lasufra,facebook,website,youtube,snapchat,image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getYoutube() {
            return youtube;
        }

        public void setYoutube(String youtube) {
            this.youtube = youtube;
        }

        public String getSnapchat() {
            return snapchat;
        }

        public void setSnapchat(String snapchat) {
            this.snapchat = snapchat;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getInstagram() {
            return instagram;
        }

        public void setInstagram(String instagram) {
            this.instagram = instagram;
        }

        public String getTwitter() {
            return twitter;
        }

        public void setTwitter(String twitter) {
            this.twitter = twitter;
        }

        public String getLasufra() {
            return lasufra;
        }

        public void setLasufra(String lasufra) {
            this.lasufra = lasufra;
        }

        public String getFacebook() {
            return facebook;
        }

        public void setFacebook(String facebook) {
            this.facebook = facebook;
        }
    }


}
