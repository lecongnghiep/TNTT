package com.example.appbh.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product  implements Serializable {

@SerializedName("mId")
@Expose
private int mId;
@SerializedName("mName")
@Expose
private String mName;
@SerializedName("mPrice")
@Expose
private Integer mPrice;
@SerializedName("mPicture")
@Expose
private String mPicture;
@SerializedName("mDescription")
@Expose
private String mDescription;
@SerializedName("mIdProduct")
@Expose
private int mIdProduct;

    public Product(int mId, String mName, Integer mPrice, String mPicture, String mDescription, int mIdProduct) {
        this.mId = mId;
        this.mName = mName;
        this.mPrice = mPrice;
        this.mPicture = mPicture;
        this.mDescription = mDescription;
        this.mIdProduct = mIdProduct;
    }

    public int getMId() {
return mId;
}

public void setMId(int mId) {
this.mId = mId;
}

public String getMName() {
return mName;
}

public void setMName(String mName) {
this.mName = mName;
}

public Integer getMPrice() {
return mPrice;
}

public void setMPrice(Integer mPrice) {
this.mPrice = mPrice;
}

public String getMPicture() {
return mPicture;
}

public void setMPicture(String mPicture) {
this.mPicture = mPicture;
}

public String getMDescription() {
return mDescription;
}

public void setMDescription(String mDescription) {
this.mDescription = mDescription;
}

public int getMIdProduct() {
return mIdProduct;
}

public void setMIdProduct(int mIdProduct) {
this.mIdProduct = mIdProduct;
}

}