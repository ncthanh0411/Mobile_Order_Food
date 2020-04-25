package com.example.food_order_application_2.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String Name;
    private String Email;
    private String Phone;
    String Image;

    public User() {
    }

    public User(String name, String email, String phone, String image) {
        Name = name;
        Email = email;
        Phone = phone;
        Image = image;
    }

    protected User(Parcel in) {
        Name = in.readString();
        Email = in.readString();
        Phone = in.readString();
        Image =in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(Email);
        dest.writeString(Phone);
        dest.writeString(Image);
    }
}
