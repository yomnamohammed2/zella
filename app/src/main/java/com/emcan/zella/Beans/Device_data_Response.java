package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Device_data_Response {
    ArrayList<Device_data> product;
    int success;

    public ArrayList<Device_data> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Device_data> product) {
        this.product = product;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public  class  Device_data{

        String location_lat,location_long,city_name,country_name,device_type,device_details_id;

        public String getLocation_lat() {
            return location_lat;
        }

        public void setLocation_lat(String location_lat) {
            this.location_lat = location_lat;
        }

        public String getLocation_long() {
            return location_long;
        }

        public void setLocation_long(String location_long) {
            this.location_long = location_long;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getCountry_name() {
            return country_name;
        }

        public void setCountry_name(String country_name) {
            this.country_name = country_name;
        }

        public String getDevice_type() {
            return device_type;
        }

        public void setDevice_type(String device_type) {
            this.device_type = device_type;
        }

        public String getDevice_details_id() {
            return device_details_id;
        }

        public void setDevice_details_id(String device_details_id) {
            this.device_details_id = device_details_id;
        }
    }

}
