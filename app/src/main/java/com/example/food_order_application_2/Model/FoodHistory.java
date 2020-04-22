package com.example.food_order_application_2.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

public class FoodHistory implements Parcelable {
    private String id;
    private String ProductId;
    private int Quantity;
    private int Price;
    private String Img;

    public FoodHistory() {
    }

    public FoodHistory(String id, String productId, int quantity, int price, String img) {
        this.id = id;
        ProductId = productId;
        Quantity = quantity;
        Price = price;
        Img = img;
    }

    protected FoodHistory(Parcel in) {
        id = in.readString();
        ProductId = in.readString();
        Quantity = in.readInt();
        Price = in.readInt();
        Img = in.readString();
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

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
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
        dest.writeString(Img);
    }
}
