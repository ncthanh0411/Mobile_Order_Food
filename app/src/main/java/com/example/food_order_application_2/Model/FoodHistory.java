package com.example.food_order_application_2.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

public class FoodHistory implements Parcelable {
    String id;
    String ProductId;
    int Quantity;
    int Price;

    public FoodHistory() {
    }

    public FoodHistory(String id, String productId, int quantity, int price) {
        this.id = id;
        ProductId = productId;
        Quantity = quantity;
        Price = price;
    }

    protected FoodHistory(Parcel in) {
        id = in.readString();
        ProductId = in.readString();
        Quantity = in.readInt();
        Price = in.readInt();
    }

    public static final Creator<FoodHistory> CREATOR = new Creator<FoodHistory>() {
        @Override
        public FoodHistory createFromParcel(Parcel in) {
            return new FoodHistory(in);
        }

        @Override
        public FoodHistory[] newArray(int size) {
            return new FoodHistory[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(ProductId);
        dest.writeInt(Quantity);
        dest.writeInt(Price);
    }
}
