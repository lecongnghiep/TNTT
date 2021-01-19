package com.example.appbh.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbh.Adapter.PhoneAdapter;
import com.example.appbh.Adapter.ProductAdapter;
import com.example.appbh.Model.Product;
import com.example.appbh.R;
import com.example.appbh.Service.APIService;
import com.example.appbh.Service.CheckConnection;
import com.example.appbh.Service.DataServiec;
import com.example.appbh.Service.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.POST;

public class PhoneActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView lsViewPhone;
    PhoneAdapter adapter;
    ArrayList<Product> arrayProductPhone;
    int idKey = 1;
    int page = 1 ;
    View view;
    boolean isLoading = false;
    mHandler mHandler;
    boolean limitdata = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        initView();
       if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            getDataIntent();
            actionBar();
            getDataVolley(page);
       // getData(idKey);
            loadMoreData();
        }else {
           CheckConnection.missConnection(getApplicationContext(),"No internet");
       }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
//        return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menucart:
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadMoreData() {
        lsViewPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),DetailProductActivity.class);
                intent.putExtra("dataproduct",arrayProductPhone.get(position));
                startActivity(intent);
            }
        });

        lsViewPhone.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount == totalItemCount &&  totalItemCount != 0 && isLoading == false && limitdata == false){
                    isLoading = true; // no keo'
                    mThreadData mThreadData = new mThreadData();
                    mThreadData.start();
                }
            }
        });
    }

    private void getDataVolley(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String link = Server.linkphone+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int mId  ;
                Integer mPrice = 0;
                String mName = "", mPicture = "", mDescription = "";
                int mIdProduct = 0;
                if(response!= null && response.length() != 2){
                    lsViewPhone.removeFooterView(view);// xoa' progress khi da load xog
                    try {
                        JSONArray jsonArray  = new JSONArray(response);
                        for (int i = 0 ; i < jsonArray.length() ; i ++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            mId = object.getInt("mId");
                            mIdProduct = object.getInt("mIdProduct");
                            mName = object.getString("mName");
                            mPicture = object.getString("mPicture");
                            mDescription = object.getString("mDescription");
                            mPrice = object.getInt("mPrice");
                            arrayProductPhone.add(new Product(mId,mName,mPrice,mPicture,mDescription,mIdProduct));
                            adapter.notifyDataSetChanged();
//                            adapter = new PhoneAdapter(getApplicationContext(),arrayProductPhone);
//                            lsViewPhone.setAdapter(adapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limitdata = true;
                    lsViewPhone.removeFooterView(view); // xoa' khi da~ het data
                    CheckConnection.missConnection(getApplicationContext(),"het' du lieu roi bro");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String , String> param = new HashMap<String, String>();
                param.put("idproduct",String.valueOf(idKey));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void getDataIntent() {
        idKey = getIntent().getIntExtra("keykindproduct",-1);
        Log.d("aaa",idKey + "");

    }

    private void getData(int idproduct) {
        lsViewPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),DetailProductActivity.class);
                intent.putExtra("dataproduct",arrayProductPhone.get(position));
                startActivity(intent);
            }
        });

        DataServiec data = APIService.getData();
        Call<List<Product>>  call = data.getProDuct(idproduct);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, retrofit2.Response<List<Product>> response) {
                arrayProductPhone = (ArrayList<Product>) response.body();
                adapter = new PhoneAdapter(getApplicationContext(),arrayProductPhone);
                lsViewPhone.setAdapter(adapter);
                Log.d("aaaa",arrayProductPhone.get(0).getMName());

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("aaaa",t.getMessage());
            }
        });
    }

    private void actionBar() {
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
        toolbar = findViewById(R.id.toobarPhone);
        lsViewPhone = findViewById(R.id.lsViewPhone);
        arrayProductPhone = new ArrayList<>();
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();
        adapter = new PhoneAdapter(getApplicationContext(),arrayProductPhone);
        lsViewPhone.setAdapter(adapter);

    }
    public class mHandler extends Handler{ //loading data trc khi click (quan? ly')

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0 :
                    lsViewPhone.addFooterView(view);
                    break;
                case 1 :
//                    page++;
                    getDataVolley(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class mThreadData extends Thread{ //nv

        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                mThreadData.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1); // lien ket quanly' vs nv
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
