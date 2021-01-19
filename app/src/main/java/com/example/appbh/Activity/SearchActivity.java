package com.example.appbh.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.appbh.Adapter.SearchProductAdapter;
import com.example.appbh.Model.Product;
import com.example.appbh.R;
import com.example.appbh.Service.APIService;
import com.example.appbh.Service.DataServiec;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.PendingIntent.getActivity;

public class SearchActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rcViewSearch;
    TextView txtNoData;
    View view;
    SearchProductAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.menuSearch);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchProduct(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }
    private void searchProduct(String mKey) {
        DataServiec  data =APIService.getData();
        Call<List<Product>> call =data.getSearchProduct(mKey);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                ArrayList<Product> arrayList = (ArrayList<Product>) response.body();
                if(arrayList.size() > 0 ){
                    adapter = new SearchProductAdapter(getApplicationContext(),arrayList);
                    LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rcViewSearch.setLayoutManager(linearLayoutManager);
                    rcViewSearch.setAdapter(adapter);
                    txtNoData.setVisibility(View.GONE);
                    rcViewSearch.setVisibility(View.VISIBLE);
                }else {
                    rcViewSearch.setVisibility(View.GONE);
                    txtNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private void initView() {
        toolbar        = findViewById(R.id.toolbarSearchProduct);
        rcViewSearch   = findViewById(R.id.rcViewSearchProduct);
        txtNoData      = findViewById(R.id.txtNoData);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

    }


}
