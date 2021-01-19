package com.example.appbh.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbh.R;
import com.example.appbh.Service.APIService;
import com.example.appbh.Service.CheckConnection;
import com.example.appbh.Service.DataServiec;
import com.example.appbh.Service.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class InformationClientActivity extends AppCompatActivity {
    EditText edtName, edtPhone , edtEmail;
    Button btnConfirm , btnBack;
     String mName;
     String mPhone;
     String mEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_client);
        initView();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            eventButton();
        }else {
            CheckConnection.missConnection(getApplicationContext(),"Bạn Kiểm Tra Lại Kết Nối");
        }
    }

    private void eventButton() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 mName = edtName.getText().toString().trim();
                 mEmail = edtEmail.getText().toString().trim();
                 mPhone = edtPhone.getText().toString().trim();
                if(mName.length() > 0 && mEmail.length() > 0 && mPhone.length() > 0){
                 //sentData();
                        sentDataVolley();

                }else {
                    CheckConnection.missConnection(getApplicationContext(),"Chưa Nhập Đủ Thông Tin Kìa Bro");
                }
            }
        });
    }

    private void sentData() {
        DataServiec data = APIService.getData();
        Call<String> callBack = data.insertClient(mName,mPhone,mEmail);
        callBack.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                String result = response.body();
                if(result.equals("success")){
                    Toast.makeText(InformationClientActivity.this, "okkkkkkkkk", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void sentDataVolley() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.linkcart, new Response.Listener<String>() {
            @Override
            public void onResponse(final String idorder) {
                if (Integer.parseInt(idorder) > 0) {
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest request = new StringRequest(Request.Method.POST, Server.linkcartorder, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("1")) {
                                MainActivity.arrayCart.clear();
                                CheckConnection.missConnection(getApplicationContext(), "Bạn Đã Thêm Dữ Liệu Giỏ Hàng Thành Công");
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                CheckConnection.missConnection(getApplicationContext(), "Mời Bạn Tiếp Tục Mua Hàng");
                            } else {
                                CheckConnection.missConnection(getApplicationContext(), "Lỗi ");
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            JSONArray jsonArray = new JSONArray();
                            for (int i = 0; i < MainActivity.arrayCart.size(); i++) {
                                JSONObject object = new JSONObject();
                                try {
                                    object.put("madonhang", idorder);
                                    object.put("masanpham", MainActivity.arrayCart.get(i).getmId());
                                    object.put("tensanpham", MainActivity.arrayCart.get(i).getmName());
                                    object.put("giasanpham", MainActivity.arrayCart.get(i).getmPrice());
                                    object.put("soluongsanpham", MainActivity.arrayCart.get(i).getmAmount());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jsonArray.put(object);
                            }
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("json", jsonArray.toString());
                            return hashMap;
                        }
                    };
                    queue.add(request);
                }else {
                    CheckConnection.missConnection(getApplicationContext(),"Vui Lòng Điền Đủ Thông Tin");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("nameClient", mName);
                param.put("phoneClient", mPhone);
                param.put("emailClient", mEmail);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void initView() {
        edtEmail = findViewById(R.id.edtEmailClient);
        edtPhone = findViewById(R.id.edtPhoneClient);
        edtName= findViewById(R.id.edtNameClient);
        btnConfirm = findViewById(R.id.btnConfirmClient);
        btnBack = findViewById(R.id.btnBackClient);

    }
}
