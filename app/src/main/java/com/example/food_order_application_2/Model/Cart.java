package com.example.food_order_application_2.Model;

public class Cart {
    String ProductId;
    String UserID;
    int Quantity;

    public Cart(String productId, String userID, int quantity) {
        ProductId = productId;
        UserID = userID;
        Quantity = quantity;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
