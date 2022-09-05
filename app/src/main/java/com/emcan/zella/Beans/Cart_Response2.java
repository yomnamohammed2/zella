package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Cart_Response2 {
    int success;
    String message;
    ArrayList<CartModel2> product;


    public ArrayList<CartModel2> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<CartModel2> product) {
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


    public static class CartModel2 {

        String cart_id, sub_category_id, sub_category_name, sub_category_desc, sub_category_image, size_id, size_name, size_price, addition_id, addition_name,
                addition_price, quantity, client_id, price, note, remove_id,
                type, potato_id, drink_id, spicy_type, drinks_name, potatos_name,summary,vat,vat_value,saudia_currency_value,charge;

        ArrayList<CartResponse.Addition_Model> addition;
        ArrayList<Remove_Response.Removes> remove;

        public String getCharge() {
            return charge;
        }

        public void setCharge(String charge) {
            this.charge = charge;
        }

        public String getSaudia_currency_value() {
            return saudia_currency_value;
        }

        public void setSaudia_currency_value(String saudia_currency_value) {
            this.saudia_currency_value = saudia_currency_value;
        }


        public String getVat() {
            return vat;
        }

        public void setVat(String vat) {
            this.vat = vat;
        }

        public String getVat_value() {
            return vat_value;
        }

        public void setVat_value(String vat_value) {
            this.vat_value = vat_value;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getDrinks_name() {
            return drinks_name;
        }

        public void setDrinks_name(String drinks_name) {
            this.drinks_name = drinks_name;
        }

        public String getPotatos_name() {
            return potatos_name;
        }

        public void setPotatos_name(String potatos_name) {
            this.potatos_name = potatos_name;
        }

        public ArrayList<Remove_Response.Removes> getRemove() {
            return remove;
        }

        public void setRemove(ArrayList<Remove_Response.Removes> remove) {
            this.remove = remove;
        }

        String total_amount, check_discount, discount_percentage, total_amount_after_disc;

        public String getSpicy_type() {
            return spicy_type;
        }

        public void setSpicy_type(String spicy_type) {
            this.spicy_type = spicy_type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPotato_id() {
            return potato_id;
        }

        public void setPotato_id(String potato_id) {
            this.potato_id = potato_id;
        }

        public String getDrink_id() {
            return drink_id;
        }

        public void setDrink_id(String drink_id) {
            this.drink_id = drink_id;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getCheck_discount() {
            return check_discount;
        }

        public void setCheck_discount(String check_discount) {
            this.check_discount = check_discount;
        }

        public String getDiscount_percentage() {
            return discount_percentage;
        }

        public void setDiscount_percentage(String discount_percentage) {
            this.discount_percentage = discount_percentage;
        }

        public String getTotal_amount_after_disc() {
            return total_amount_after_disc;
        }

        public void setTotal_amount_after_disc(String total_amount_after_disc) {
            this.total_amount_after_disc = total_amount_after_disc;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getRemove_id() {
            return remove_id;
        }

        public void setRemove_id(String remove_id) {
            this.remove_id = remove_id;
        }

        public ArrayList<CartResponse.Addition_Model> getAddition() {
            return addition;
        }

        public void setAddition(ArrayList<CartResponse.Addition_Model> addition) {
            this.addition = addition;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCart_id() {
            return cart_id;
        }

        public void setCart_id(String cart_id) {
            this.cart_id = cart_id;
        }

        public String getSub_category_id() {
            return sub_category_id;
        }

        public void setSub_category_id(String sub_category_id) {
            this.sub_category_id = sub_category_id;
        }

        public String getSize_id() {
            return size_id;
        }

        public void setSize_id(String size_id) {
            this.size_id = size_id;
        }

        public String getSize_name() {
            return size_name;
        }

        public void setSize_name(String size_name) {
            this.size_name = size_name;
        }

        public String getSize_price() {
            return size_price;
        }

        public void setSize_price(String size_price) {
            this.size_price = size_price;
        }

        public String getAddition_id() {
            return addition_id;
        }

        public void setAddition_id(String addition_id) {
            this.addition_id = addition_id;
        }

        public String getAddition_name() {
            return addition_name;
        }

        public void setAddition_name(String addition_name) {
            this.addition_name = addition_name;
        }

        public String getAddition_price() {
            return addition_price;
        }

        public void setAddition_price(String addition_price) {
            this.addition_price = addition_price;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getClient_id() {
            return client_id;
        }

        public void setClient_id(String client_id) {
            this.client_id = client_id;
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
    }

    public class Addition_Model {
        String addition_name, addition_price;

        public String getAddition_name() {
            return addition_name;
        }

        public void setAddition_name(String addition_name) {
            this.addition_name = addition_name;
        }

        public String getAddition_price() {
            return addition_price;
        }

        public void setAddition_price(String addition_price) {
            this.addition_price = addition_price;
        }
    }

}


