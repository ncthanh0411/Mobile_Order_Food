package com.example.food_order_application_2.Model;

public class Order {
    String food_id;
    String address;
    String total_price;
    String user_id;


    public Order() {
    }

    public Order(String food_id, String address, String total_price, String user_id) {
        this.food_id = food_id;
        this.address = address;
        this.total_price = total_price;
        this.user_id = user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
}
