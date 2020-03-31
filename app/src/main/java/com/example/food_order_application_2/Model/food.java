package com.example.food_order_application_2.Model;

public class food {
    String name;
    String detail;
    int price;
    String img;

    public food() {
    }

    public food(String name, String detail, int price, String img) {
        this.name = name;
        this.detail = detail;
        this.price = price;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
