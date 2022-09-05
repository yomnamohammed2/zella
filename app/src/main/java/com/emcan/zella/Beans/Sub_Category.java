package com.emcan.zella.Beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Sub_Category implements Serializable,Parcelable{
    String sub_category_fav,sub_category_image,sub_category_id,sub_category_name,sub_category_desc,parent_category_id,sub_category_image_id;
    String client_id,fav_id,evaluate,image,parent_category_name,type,spicy_type,spicy_show,sub_category_logo,size_price;

   String  slider_image,slider_type;
   String addition_type,removes_type;
    ArrayList<Prices_Model.Price> sizes;

    ArrayList<Image_Model> images;


    protected Sub_Category(Parcel in) {
        sub_category_fav = in.readString();
        sub_category_image = in.readString();
        sub_category_id = in.readString();
        sub_category_name = in.readString();
        sub_category_desc = in.readString();
        parent_category_id = in.readString();
        sub_category_image_id = in.readString();
        client_id = in.readString();
        fav_id = in.readString();
        evaluate = in.readString();
        image = in.readString();
        parent_category_name = in.readString();
        type = in.readString();
        spicy_type = in.readString();
        spicy_show = in.readString();
        sub_category_logo = in.readString();
        size_price = in.readString();
        slider_image = in.readString();
        slider_type = in.readString();
        addition_type = in.readString();
        removes_type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sub_category_fav);
        dest.writeString(sub_category_image);
        dest.writeString(sub_category_id);
        dest.writeString(sub_category_name);
        dest.writeString(sub_category_desc);
        dest.writeString(parent_category_id);
        dest.writeString(sub_category_image_id);
        dest.writeString(client_id);
        dest.writeString(fav_id);
        dest.writeString(evaluate);
        dest.writeString(image);
        dest.writeString(parent_category_name);
        dest.writeString(type);
        dest.writeString(spicy_type);
        dest.writeString(spicy_show);
        dest.writeString(sub_category_logo);
        dest.writeString(size_price);
        dest.writeString(slider_image);
        dest.writeString(slider_type);
        dest.writeString(addition_type);
        dest.writeString(removes_type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Sub_Category> CREATOR = new Creator<Sub_Category>() {
        @Override
        public Sub_Category createFromParcel(Parcel in) {
            return new Sub_Category(in);
        }

        @Override
        public Sub_Category[] newArray(int size) {
            return new Sub_Category[size];
        }
    };

    public ArrayList<Image_Model> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image_Model> images) {
        this.images = images;
    }

    public String getAddition_type() {
        return addition_type;
    }

    public void setAddition_type(String addition_type) {
        this.addition_type = addition_type;
    }

    public String getRemoves_type() {
        return removes_type;
    }

    public void setRemoves_type(String removes_type) {
        this.removes_type = removes_type;
    }

    public String getSlider_image() {
        return slider_image;
    }

    public void setSlider_image(String slider_image) {
        this.slider_image = slider_image;
    }

    public String getSlider_type() {
        return slider_type;
    }

    public void setSlider_type(String slider_type) {
        this.slider_type = slider_type;
    }

    public ArrayList<Prices_Model.Price> getSizes() {
        return sizes;
    }

    public void setSizes(ArrayList<Prices_Model.Price> sizes) {
        this.sizes = sizes;
    }


    public String getSize_price() {
        return size_price;
    }

    public void setSize_price(String size_price) {
        this.size_price = size_price;
    }

    public String getSub_category_logo() {
        return sub_category_logo;
    }

    public void setSub_category_logo(String sub_category_logo) {
        this.sub_category_logo = sub_category_logo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParent_category_name() {
        return parent_category_name;
    }

    public void setParent_category_name(String parent_category_name) {
        this.parent_category_name = parent_category_name;
    }


    public String getSpicy_type() {
        return spicy_type;
    }

    public void setSpicy_type(String spicy_type) {
        this.spicy_type = spicy_type;
    }
      public String getSpicy_show() {
        return spicy_show;
    }

    public void setSpicy_show(String spicy_show) {
        this.spicy_show = spicy_show;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getFav_id() {
        return fav_id;
    }

    public void setFav_id(String fav_id) {
        this.fav_id = fav_id;
    }

    public Sub_Category() {
    }

    public String getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(String sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public String getSub_category_name() {
        return sub_category_name;
    }

    public void setSub_category_name(String sub_category_name) {
        this.sub_category_name = sub_category_name;
    }

    public String getSub_category_desc() {
        return sub_category_desc;
    }

    public void setSub_category_desc(String sub_category_desc) {
        this.sub_category_desc = sub_category_desc;
    }

    public String getSub_category_image() {
        return sub_category_image;
    }

    public void setSub_category_image(String sub_category_image) {
        this.sub_category_image = sub_category_image;
    }




    public String getSub_category_fav() {
        return sub_category_fav;
    }

    public void setSub_category_fav(String sub_category_fav) {
        this.sub_category_fav = sub_category_fav;
    }



    public String getParent_category_id() {
        return parent_category_id;
    }

    public void setParent_category_id(String parent_category_id) {
        this.parent_category_id = parent_category_id;
    }

    public String getSub_category_image_id() {
        return sub_category_image_id;
    }

    public void setSub_category_image_id(String sub_category_image_id) {
        this.sub_category_image_id = sub_category_image_id;
    }

    public String getSub_category_image_name() {
        return sub_category_image;
    }

    public void setSub_category_image_name(String sub_category_image_name) {
        this.sub_category_image= sub_category_image_name;
    }



}
