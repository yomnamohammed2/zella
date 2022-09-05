package com.emcan.zella.Beans;

import android.os.Parcel;
import android.os.Parcelable;

public class Parent_Category implements Parcelable{

        String parent_category_id,parent_category_name,parent_category_desc,parent_category_image,type,parent_type;

        String  addition_type,removes_type;


        public Parent_Category() {
        }

    protected Parent_Category(Parcel in) {
        parent_category_id = in.readString();
        parent_category_name = in.readString();
        parent_category_desc = in.readString();
        parent_category_image = in.readString();
        type = in.readString();
        parent_type = in.readString();
        addition_type = in.readString();
        removes_type = in.readString();
    }

    public static final Creator<Parent_Category> CREATOR = new Creator<Parent_Category>() {
        @Override
        public Parent_Category createFromParcel(Parcel in) {
            return new Parent_Category(in);
        }

        @Override
        public Parent_Category[] newArray(int size) {
            return new Parent_Category[size];
        }
    };

    public String getParent_category_id() {
        return parent_category_id;
    }

    public void setParent_category_id(String parent_category_id) {
        this.parent_category_id = parent_category_id;
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

    public String getParent_type() {
        return parent_type;
    }

    public void setParent_type(String parent_type) {
        this.parent_type = parent_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public String getParent_categorey_id() {
            return parent_category_id;
        }

        public void setParent_categorey_id(String parent_categorey_id) {
            this.parent_category_id= parent_categorey_id;
        }

        public String getParent_categorey_name() {
            return parent_category_name;
        }

        public void setParent_category_name(String parent_category_name) {
            this.parent_category_name= parent_category_name;
        }
        public String getParent_categorey_desc() {
            return parent_category_desc;
        }

        public void setParent_categorey_desc(String parent_categorey_desc) {
            this.parent_category_desc= parent_categorey_desc;
        }
        public String getParent_categorey_image() {
            return parent_category_image;
        }

        public void setParent_categorey_image(String parent_categorey_image) {
            this.parent_category_image= parent_categorey_image;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(parent_category_id);
        parcel.writeString(parent_category_name);
        parcel.writeString(parent_category_desc);
        parcel.writeString(parent_category_image);
        parcel.writeString(type);
    }
}

