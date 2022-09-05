package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Meal_Additions {

    ArrayList<Potato_Drinks> product;
    int success;

    public ArrayList<Potato_Drinks> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Potato_Drinks> product) {
        this.product = product;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public class Potato_Drinks{
        ArrayList <Potatos_Model> potatos;
        ArrayList<Drinks_Model> drinks;


        public ArrayList<Potatos_Model> getPotatos() {
            return potatos;
        }

        public void setPotatos(ArrayList<Potatos_Model> potatos) {
            this.potatos = potatos;
        }

        public ArrayList<Drinks_Model> getDrinks() {
            return drinks;
        }

        public void setDrinks(ArrayList<Drinks_Model> drinks) {
            this.drinks = drinks;
        }
    }

    public class Potatos_Model{
        String potato_id,title,addition_price;
        int check;

        public int getCheck() {
            return check;
        }

        public void setCheck(int check) {
            this.check = check;
        }

        public String getAddition_price() {
            return addition_price;
        }

        public void setAddition_price(String addition_price) {
            this.addition_price = addition_price;
        }

        public String getPotato_id() {
            return potato_id;
        }

        public void setPotato_id(String potato_id) {
            this.potato_id = potato_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public class Drinks_Model{
        String drink_id,title,addition_price;
        int check;

        public int getCheck() {
            return check;
        }

        public void setCheck(int check) {
            this.check = check;
        }

        public String getAddition_price() {
            return addition_price;
        }

        public void setAddition_price(String addition_price) {
            this.addition_price = addition_price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDrink_id() {
            return drink_id;
        }

        public void setDrink_id(String drink_id) {
            this.drink_id = drink_id;
        }
    }
}
