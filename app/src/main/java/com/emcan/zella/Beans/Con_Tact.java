package com.emcan.zella.Beans;

import java.util.ArrayList;

public class Con_Tact {

    ArrayList<Contact> contacts;
    int success;
String device_details_id,client_id;

    public Con_Tact(ArrayList<Contact> contacts, String device_details_id, String client_id) {
        this.contacts = contacts;
        this.device_details_id = device_details_id;
        this.client_id = client_id;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getDevice_details_id() {
        return device_details_id;
    }

    public void setDevice_details_id(String device_details_id) {
        this.device_details_id = device_details_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public Con_Tact(ArrayList<Contact> contacts) {
        this.contacts = contacts;

    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }



    public static class Contact{
       String name,phone_number;

        public Contact(String name, String phone_number) {
            this.name = name;
            this.phone_number = phone_number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }


    }
}
