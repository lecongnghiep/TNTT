package com.example.appbh.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart {
    @SerializedName("mId")
    @Expose
    private int mId;

    @SerializedName("mName")
    @Expose
    private String mName;

    @SerializedName("mPrice")
    @Expose
    private long mPrice;

    @SerializedName("mPicture")
    @Expose
    private String mPicture;

    @SerializedName("mAmount")
    @Expose
    private int mAmount;

    public Cart(int mId, String mName, long mPrice, String mPicture, int mAmount) {
        this.mId = mId;
        this.mName = mName;
        this.mPrice = mPrice;
        this.mPicture = mPicture;
        this.mAmount = mAmount;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public long getmPrice() {
        return mPrice;
    }

    public void setmPrice(long mPrice) {
        this.mPrice = mPrice;
    }

    public String getmPicture() {
        return mPicture;
    }

    public void setmPicture(String mPicture) {
        this.mPicture = mPicture;
    }

    public int getmAmount() {
        return mAmount;
    }

    public void setmAmount(int mAmount) {
        this.mAmount = mAmount;
    }
}
