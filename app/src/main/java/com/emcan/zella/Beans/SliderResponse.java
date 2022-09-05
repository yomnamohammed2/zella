package com.emcan.zella.Beans;

import java.util.ArrayList;

public class SliderResponse {

    private ArrayList<Sub_Category> slider;
    private ArrayList<Sub_Category> latest;
    private ArrayList<Parent_Category> categories;
    private ArrayList<Sub_Category>  most_selling;
    private ArrayList<Sub_Category>  offers;
    private ArrayList<PopupModel>  popUp;

    private int success;
    int cart_count;

    public ArrayList<PopupModel> getPopUp() {
        return popUp;
    }

    public void setPopUp(ArrayList<PopupModel> popUp) {
        this.popUp = popUp;
    }

    public ArrayList<Sub_Category> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<Sub_Category> offers) {
        this.offers = offers;
    }

    public ArrayList<Sub_Category> getMost_selling() {
        return most_selling;
    }

    public void setMost_selling(ArrayList<Sub_Category> most_selling) {
        this.most_selling = most_selling;
    }

    public ArrayList<Parent_Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Parent_Category> categories) {
        this.categories = categories;
    }

    public int getCart_count() {
        return cart_count;
    }

    public void setCart_count(int cart_count) {
        this.cart_count = cart_count;
    }

    public ArrayList<Sub_Category> getLatest() {
        return latest;
    }

    public void setLatest(ArrayList<Sub_Category> latest) {
        this.latest = latest;
    }

    public ArrayList<Sub_Category> getSlider() {
        return slider;
    }

    public void setSlider(ArrayList<Sub_Category> slider) {
        this.slider = slider;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}
