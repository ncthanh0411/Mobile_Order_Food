package com.example.food_order_application_2;

public class Food  {
    String name;
    String description;
    String image;
    int price;

    public Food(String name, String description, String image, int price) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDetail(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
