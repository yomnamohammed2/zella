package com.emcan.zella.Beans;

public class PopupModel {

    String image,type,sub_category_image;
    Sub_Category item_details;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSub_category_image() {
        return sub_category_image;
    }

    public void setSub_category_image(String sub_category_image) {
        this.sub_category_image = sub_category_image;
    }

    public Sub_Category getItem_details() {
        return item_details;
    }

    public void setItem_details(Sub_Category item_details) {
        this.item_details = item_details;
    }
}
