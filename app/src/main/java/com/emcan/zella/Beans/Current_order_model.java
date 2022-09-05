package com.emcan.zella.Beans;

import java.io.Serializable;
import java.util.ArrayList;

public class Current_order_model implements Serializable{
    ArrayList<Current_order> product;
    int success;
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Current_order> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Current_order> product) {
        this.product = product;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public class Current_order implements Serializable {
        String client_id,order_date,order_status,order_follow,deliver_id,
                order_id,total_price,discount_percentage,charge_cost,total_price_without_charge,net_price,vat_percentage
                ,vat_added,total_amount_after_disc,payment;

        ArrayList<Order_item> items;
        ArrayList<ClientAddress> client_address;
        ArrayList<DriverData> driver_data;

        public ArrayList<DriverData> getDriver_data() {
            return driver_data;
        }

        public void setDriver_data(ArrayList<DriverData> driver_data) {
            this.driver_data = driver_data;
        }

        public ArrayList<ClientAddress> getClient_address() {
            return client_address;
        }

        public void setClient_address(ArrayList<ClientAddress> client_address) {
            this.client_address = client_address;
        }

        public String getPayment() {
            return payment;
        }

        public void setPayment(String payment) {
            this.payment = payment;
        }

        public String getTotal_amount_after_disc() {
            return total_amount_after_disc;
        }

        public void setTotal_amount_after_disc(String total_amount_after_disc) {
            this.total_amount_after_disc = total_amount_after_disc;
        }


        public String getVat_percentage() {
            return vat_percentage;
        }

        public void setVat_percentage(String vat_percentage) {
            this.vat_percentage = vat_percentage;
        }

        public String getVat_added() {
            return vat_added;
        }

        public void setVat_added(String vat_added) {
            this.vat_added = vat_added;
        }

        public String getDeliver_id() {
            return deliver_id;
        }

        public void setDeliver_id(String deliver_id) {
            this.deliver_id = deliver_id;
        }

        public String getDiscount_percentage() {
            return discount_percentage;
        }

        public void setDiscount_percentage(String discount_percentage) {
            this.discount_percentage = discount_percentage;
        }

        public String getCharge_cost() {
            return charge_cost;
        }

        public void setCharge_cost(String charge_cost) {
            this.charge_cost = charge_cost;
        }

        public String getTotal_price_without_charge() {
            return total_price_without_charge;
        }

        public void setTotal_price_without_charge(String total_price_without_charge) {
            this.total_price_without_charge = total_price_without_charge;
        }

        public String getNet_price() {
            return net_price;
        }

        public void setNet_price(String net_price) {
            this.net_price = net_price;
        }

        public String getClient_id() {
            return client_id;
        }

        public void setClient_id(String client_id) {
            this.client_id = client_id;
        }

        public String getOrder_date() {
            return order_date;
        }

        public void setOrder_date(String order_date) {
            this.order_date = order_date;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getOrder_follow() {
            return order_follow;
        }

        public void setOrder_follow(String order_follow) {
            this.order_follow = order_follow;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }


        public ArrayList<Order_item> getItems() {
            return items;
        }

        public void setItems(ArrayList<Order_item> items) {
            this.items = items;
        }
    }
    public class Order_item implements Serializable{
        String cart_id,sub_category_name,sub_category_desc,sub_category_image,deliver_id,
                size_name,size_price,addition_name,addition_price,quantity,price;

        ArrayList <CartResponse.Addition_Model> addition;

        String drinks_name,potatos_name,type;

        ArrayList <Remove_Response.Removes> remove;
        ArrayList <Remove_Response.Removes> side;

        public ArrayList<Remove_Response.Removes> getSide() {
            return side;
        }

        public void setSide(ArrayList<Remove_Response.Removes> side) {
            this.side = side;
        }

        public String getDeliver_id() {
            return deliver_id;
        }

        public void setDeliver_id(String deliver_id) {
            this.deliver_id = deliver_id;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ArrayList<Remove_Response.Removes> getRemove() {
            return remove;
        }

        public void setRemove(ArrayList<Remove_Response.Removes> remove) {
            this.remove = remove;
        }

        public ArrayList<CartResponse.Addition_Model> getAddition() {
            return addition;
        }

        public void setAddition(ArrayList<CartResponse.Addition_Model> addition) {
            this.addition = addition;
        }

        public String getCart_id() {
            return cart_id;
        }

        public void setCart_id(String cart_id) {
            this.cart_id = cart_id;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
