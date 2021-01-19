package com.example.appbh.Service;

import com.example.appbh.Model.KindProduct;
import com.example.appbh.Model.Product;
import com.example.appbh.Model.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DataServiec {
    @GET("loaisanpham.php")
    Call<List<KindProduct>> getData();

    @GET("getsanphammoi.php")
    Call<List<Product>> getDataProduct();

    @FormUrlEncoded
    @POST("getproduct.php")
    Call<List<Product>> getProDuct(@Field("idproduct") int idproduct);

    @FormUrlEncoded
    @POST("thongtinkhachhang.php")
    Call<String> insertClient(@Field("nameClient") String mName
                            ,@Field("phoneClient") String mPhone
                            ,@Field("emailClient") String mEmail);

    @FormUrlEncoded
    @POST("test.php")
    Call<List<Product>> getSearchProduct(@Field("mKey") String mKey);







    @Multipart  //gởi lên server nếu là ảnh or âm thanh .. trừ chuỗi
    @POST("uploadimage.php")
    Call<String> UploadPhoto(@Part MultipartBody.Part photo);    //vì server gởi  mảng



    @FormUrlEncoded //goi data dang chuoi~
    @POST("insert.php")
    Call<String> insertData(@Field("userName")  String taikhoan
            , @Field("passWord") String matkhau
            , @Field("email")  String email);



    @FormUrlEncoded
    @POST("login.php")
    Call<List<User>> loginData(@Field("userName") String taikhoan
            , @Field("passWord") String matkhau);

    @GET("delete.php")
    Call<String> DeleteData(@Query("iD") String iD
            , @Query("picTure") String picTure);
}
