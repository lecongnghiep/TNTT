package com.example.appbh.Service;

import com.example.appbh.Model.Product;
import com.example.appbh.Model.User;

public class APIService {
  private static final String base_url = "https://appbh06051998.000webhostapp.com/appBH/";
//    private static final String base_url = "http://192.168.1.3/appBH/";
    public static DataServiec getData(){
        return APIRetrofitClient.getClient(base_url).create(DataServiec.class);


    }
}
