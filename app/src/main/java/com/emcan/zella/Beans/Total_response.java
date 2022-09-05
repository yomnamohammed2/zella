package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Total_response {

    int success;
    String message;
    ArrayList<Tot> product;

    public ArrayList<Tot> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Tot> product) {
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

    public class Tot{
        String total_amount,check_discount,discount_percentage,total_amount_after_disc;

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

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }
    }
}
