package com.example.appbh.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appbh.Model.Cart;
import com.example.appbh.Model.User;
import com.example.appbh.R;
import com.example.appbh.Service.APIService;
import com.example.appbh.Service.DataServiec;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button btnDN,btnDK;
    EditText edtTK,edtMK;
    Toolbar toolbar;
    String taikhoan,matkhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        init();
        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(intent);
            }
        });
        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taikhoan = edtTK.getText().toString().trim();
                matkhau  = edtMK.getText().toString().trim();
                if(taikhoan.length() > 0 && matkhau.length() > 0 ){
                    DataServiec data = APIService.getData();
                    Call<List<User>> call = data.loginData(taikhoan,matkhau);
                    call.enqueue(new Callback<List<User>>() {
                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                           ArrayList<User> array = (ArrayList<User>) response.body();
                            if(array.size() >0){
                                Intent intent = new Intent(LoginActivity.this, InformationClientActivity.class);
                                Toast.makeText(LoginActivity.this, "Mời Bạn Xác Nhận Lại Thông Tin", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Tài Khoản Không Tồn Tại", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    Toast.makeText(LoginActivity.this, "Mời Bạn Nhập Tài Khoản ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView() {
        btnDK = findViewById(R.id.btnDangKy);
        btnDN = findViewById(R.id.btnDangNhap);
        edtMK = findViewById(R.id.edtMatKhau);
        edtTK = findViewById(R.id.edtTaiKhoan);
        toolbar = findViewById(R.id.toolbarLogin);
    }
}
