package com.example.appbh.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbh.Adapter.KindProductApdapter;
import com.example.appbh.Adapter.ProductAdapter;
import com.example.appbh.Model.Cart;
import com.example.appbh.Model.KindProduct;
import com.example.appbh.Model.Product;
import com.example.appbh.R;
import com.example.appbh.Service.APIService;
import com.example.appbh.Service.CheckConnection;
import com.example.appbh.Service.DataServiec;
import com.example.appbh.Service.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
//    ArrayList<String> arrayString = new ArrayList<>();
    Toolbar toolbar;
    ViewFlipper viewFlipper;//truot cho dep
    RecyclerView rcViewMain;
    NavigationView navigationView;
    ListView lsvMain;
    DrawerLayout drawerLayout;
    ArrayList<KindProduct> arrayKindProduct;
    KindProductApdapter apdapter;

    ArrayList<Product> arrayProduct;
    ProductAdapter productAdapter;
    String id = "",name = "" , picture = "";
    KindProduct kindProduct;

    public static ArrayList<Cart> arrayCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            actionBar();
            actionViewFlipper();
//            getDataVolley();
//            getDataProductVolley();

            getData();
            getDataProduct();
            catchOnItemListView();
        }else {
            CheckConnection.missConnection(getApplicationContext(),"Kiểm Tra Lại Kết Nối");
            finish();
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

    private void catchOnItemListView() {
        lsvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.missConnection(getApplicationContext(),"Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,PhoneActivity.class);
                            intent.putExtra("keykindproduct",position);
                            startActivity(intent);
                        }else {
                            CheckConnection.missConnection(getApplicationContext(),"Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LapTopActivity.class);
                            intent.putExtra("keykindproduct",position);
                            startActivity(intent);
                        }else {
                            CheckConnection.missConnection(getApplicationContext(),"Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                          // Intent intent =new Intent(MainActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.missConnection(getApplicationContext(),"Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                           Intent intent = new Intent(MainActivity.this,ContactActivity.class);
                          //  Intent intent = new Intent(MainActivity.this,RegistrationActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.missConnection(getApplicationContext(),"Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,InformationActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.missConnection(getApplicationContext(),"Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void getDataProductVolley() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.linkproductnew, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                   int mId  = 0;
                    Integer mPrice = 0;
                    String mName = "", mPicture = "", mDescription = "";
                    int mIdProduct = 0;
                    for (int i = 0 ; i < response.length() ; i++){
                        try {
                            JSONObject object =  response.getJSONObject(i);
                            mId = object.getInt("mId");
                            mIdProduct = object.getInt("mIdProduct");
                            mName = object.getString("mName");
                            mPicture = object.getString("mPicture");
                            mDescription = object.getString("mDescription");
                            mPrice = object.getInt("mPrice");
                            arrayProduct.add(new Product(mId,mName,mPrice,mPicture,mDescription,mIdProduct));
                            productAdapter = new ProductAdapter(arrayProduct,getApplicationContext());
                            rcViewMain.setHasFixedSize(true);
                            rcViewMain.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                            rcViewMain.setAdapter(productAdapter);
                            productAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("aaa",error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getDataVolley() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.link, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    for (int i = 0 ;i < response.length() ; i ++){
                        try {
                            JSONObject  object =  response.getJSONObject(i);
                            id = object.getString("mId");
                            name = object.getString("nameKindProduct");
                            picture =object.getString("pictureKindProduct");
                            arrayKindProduct.add(new KindProduct(name,picture));
                            apdapter = new KindProductApdapter(getApplicationContext(), arrayKindProduct);
                            lsvMain.setAdapter(apdapter);
                            apdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    arrayKindProduct.add(0,new KindProduct("Trang Chủ","https://laptopgiasi.vn/wp-content/uploads/2017/09/icon-trang-chu-laptopgiasi.vn_.png"));
                    arrayKindProduct.add(3,new KindProduct("Tìm Kiếm", "https://img.icons8.com/pastel-glyph/2x/search--v2.png"));
                    arrayKindProduct.add(4,new KindProduct("Liên Hệ","https://buigiastore.com/wp-content/uploads/2020/03/unnamed.png"));
                    arrayKindProduct.add(5,new KindProduct("Thông Tin","https://cdn1.iconfinder.com/data/icons/Pretty_office_icon_part_2/128/personal-information.png"));
                   // lsvMain.setAdapter(apdapter);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.missConnection(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getDataProduct() {
        DataServiec data = APIService.getData();
        Call<List<Product>> call = data.getDataProduct();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                ArrayList<Product> arrayProduct = (ArrayList<Product>) response.body();
                productAdapter = new ProductAdapter(arrayProduct,getApplicationContext());
                rcViewMain.setHasFixedSize(true);
                rcViewMain.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                rcViewMain.setAdapter(productAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private void getData() {
        DataServiec data = APIService.getData();
        Call<List<KindProduct>> callBack = data.getData();
        callBack.enqueue(new Callback<List<KindProduct>>() {
            @Override
            public void onResponse(Call<List<KindProduct>> call, Response<List<KindProduct>> response) {
                arrayKindProduct = (ArrayList<KindProduct>) response.body();
                arrayKindProduct.add(0,new KindProduct("Trang Chủ","https://laptopgiasi.vn/wp-content/uploads/2017/09/icon-trang-chu-laptopgiasi.vn_.png"));
                arrayKindProduct.add(3,new KindProduct("Tìm Kiếm", "https://img.icons8.com/pastel-glyph/2x/search--v2.png"));
                arrayKindProduct.add(4,new KindProduct("Liên Hệ","https://buigiastore.com/wp-content/uploads/2020/03/unnamed.png"));
                arrayKindProduct.add(5,new KindProduct("Thông Tin","https://cdn1.iconfinder.com/data/icons/Pretty_office_icon_part_2/128/personal-information.png"));
                apdapter = new KindProductApdapter(getApplicationContext(), arrayKindProduct);
                lsvMain.setAdapter(apdapter);
                apdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<KindProduct>> call, Throwable t) {
                Log.d("aaa",t.getMessage());
            }
        });
    }

    private void actionViewFlipper() {
        ArrayList<String> arrayString = new ArrayList<>();
        arrayString.add("https://mondrian.mashable.com/uploads%252Fcard%252Fimage%252F263361%252FThumbMacBookPro.JPG%252F950x534__filters%253Aquality%252880%2529.JPG");
        arrayString.add("https://cdn.tgdd.vn/Products/Images/44/189385/dell-inspiron-3476-8j61p11-tgdd-at.jpg");
        arrayString.add("https://cdn.tgdd.vn/Files/2020/03/31/1245842/top-5-macbook-nhan-giam-gia-duy-nhat-ngay-1-4.jpg");
        arrayString.add("https://i.a4vn.com/2013/2/14/apple-giam-gia-hang-loat-laptop-macbook-01121e.png");
        for (int i = 0 ; i <arrayString.size(); i++){
            ImageView imageView  = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(arrayString.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        Animation animationIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.custom_ani_in);
        Animation animationOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.custom_ani_out);
        viewFlipper.setInAnimation(animationIn);
        viewFlipper.setOutAnimation(animationOut);
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void initView() {
        drawerLayout   = findViewById(R.id.drawerLayout);
        toolbar        = findViewById(R.id.toolbarMain);
        viewFlipper    = findViewById(R.id.viewflipperMain);
        rcViewMain     = findViewById(R.id.rcViewMain);
        navigationView = findViewById(R.id.navigationViewMain);
        lsvMain        = findViewById(R.id.lsViewMain);
        arrayKindProduct = new ArrayList<>();
        arrayProduct = new ArrayList<>();
       //retrofix
        apdapter         =  new KindProductApdapter(getApplicationContext(),arrayKindProduct);
        lsvMain.setAdapter(apdapter);

        if(arrayCart != null){

        }else {
            arrayCart = new ArrayList<>();
        }
    }
}
