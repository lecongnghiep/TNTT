package com.example.appbh.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appbh.R;
import com.example.appbh.Service.APIService;
import com.example.appbh.Service.DataServiec;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    ImageView imgHinh;
    EditText edtDangKySV,edtMatKhauSV,edtMailSV;
    Button btnHuy,btnXN;
    int REQUSET_CODE_IMG = 123;
    String realPath = "";
    String taikhoan;
    String matkhau;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initView();
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnXN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taikhoan = edtDangKySV.getText().toString().trim();
                matkhau = edtMatKhauSV.getText().toString().trim();
                email = edtMailSV.getText().toString().trim();
                if (taikhoan.length() > 0 && matkhau.length()  > 0 && email.length() > 0) {
                    DataServiec data = APIService.getData();
                    Call<String> call = data.insertData(taikhoan, matkhau, email);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String result = response.body();
                                if(result.equals("thanhcong")){
                                    Toast.makeText(RegistrationActivity.this, "Đăng Ký Thành Công", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }else {
                    Toast.makeText(RegistrationActivity.this, "Vui Lòng Điền Đủ Thông Tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void initView() {
        edtDangKySV = findViewById(R.id.edtDangKySV);
        edtMatKhauSV = findViewById(R.id.edtMatKhauSV);
        edtMailSV   = findViewById(R.id.edtMailSV);
        btnHuy = findViewById(R.id.btnHuySV);
        btnXN = findViewById(R.id.btnXacNhanSV);
    }

}
