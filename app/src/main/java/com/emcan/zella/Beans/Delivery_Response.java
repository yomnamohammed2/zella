package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Delivery_Response
{
    ArrayList<Delivery_Model> product;
    int success;
    String message;

    public ArrayList<Delivery_Model> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Delivery_Model> product) {
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

    public class  Delivery_Model{

        String deliver_id,name,display;

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public String getDeliver_id() {
            return deliver_id;
        }

        public void setDeliver_id(String deliver_id) {
            this.deliver_id = deliver_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
