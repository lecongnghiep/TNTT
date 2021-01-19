package com.example.appbh.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KindProduct {

@SerializedName("mId")
@Expose
private int id;
@SerializedName("nameKindProduct")
@Expose
private String nameTypeProduct;
@SerializedName("pictureKindProduct")
@Expose
private String pictureTypeProduct;

    public KindProduct(String nameTypeProduct, String pictureTypeProduct) {
        this.nameTypeProduct = nameTypeProduct;
        this.pictureTypeProduct = pictureTypeProduct;
    }

//    public KindProduct(String id, String nameTypeProduct, String pictureTypeProduct) {
//        this.id = id;
//        this.nameTypeProduct = nameTypeProduct;
//        this.pictureTypeProduct = pictureTypeProduct;
//    }

    public int getId() {
return id;
}

public void setId(int id) {
this.id = id;
}

public String getNameTypeProduct() {
return nameTypeProduct;
}

public void setNameTypeProduct(String nameTypeProduct) {
this.nameTypeProduct = nameTypeProduct;
}

public String getPictureTypeProduct() {
return pictureTypeProduct;
}

public void setPictureTypeProduct(String pictureTypeProduct) {
this.pictureTypeProduct = pictureTypeProduct;
}

}