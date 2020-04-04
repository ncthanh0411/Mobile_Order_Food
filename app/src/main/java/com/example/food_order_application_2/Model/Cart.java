package com.example.food_order_application_2.Model;

import java.io.Serializable;

public class Cart implements Serializable {
    String ProductId;
    int Quantity;

    public Cart(String productId, int quantity) {
        ProductId = productId;
        Quantity = quantity;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
