package com.example.food_order_application_2.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Cart implements Parcelable {
    String ProductId;
    int Quantity;

    public Cart(String productId, int quantity) {
        ProductId = productId;
        Quantity = quantity;
    }

    protected Cart(Parcel in) {
        ProductId = in.readString();
        Quantity = in.readInt();
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ProductId);
        dest.writeInt(Quantity);
    }
}
