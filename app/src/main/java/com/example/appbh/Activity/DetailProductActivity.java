package com.example.appbh.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbh.Model.Cart;
import com.example.appbh.Model.Product;
import com.example.appbh.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DetailProductActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtName, txtPrice, txtDescription;
    ImageView imgPicture;
    Spinner spinner;
    Button btnClick;
    int  mPrice = 0 , idProdutct = 0;
    int mId = 0 ;
    String mName = "", mDescription = " ", mPicture = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        initView();
        actionToolbar();
        getDataIntent();
        eventSpinner();
        eventButton();

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

    private void eventButton() {
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
               Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);

                if(MainActivity.arrayCart.size() > 0 ){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for(int i = 0 ; i < MainActivity.arrayCart.size() ; i ++){
                        if(MainActivity.arrayCart.get(i).getmId() == mId){
                            MainActivity.arrayCart.get(i).setmAmount(MainActivity.arrayCart.get(i).getmAmount() + sl);
                            if(MainActivity.arrayCart.get(i).getmAmount() > 10 ){
                                MainActivity.arrayCart.get(i).setmAmount(10);
                            }
                            MainActivity.arrayCart.get(i).setmPrice(mPrice * MainActivity.arrayCart.get(i).getmAmount());
                            exists = true;
                        }
                    }
                    if(exists == false){
                        int sl1 = Integer.parseInt(spinner.getSelectedItem().toString());
                        long priceNew = sl1 * mPrice;
                        MainActivity.arrayCart.add(new Cart(mId,mName,priceNew,mPicture,sl1));
                    }

                }else {
                    int sl1 = Integer.parseInt(spinner.getSelectedItem().toString());
                    long priceNew = sl1 * mPrice;
                    MainActivity.arrayCart.add(new Cart(mId,mName,priceNew,mPicture,sl1));

                }
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void eventSpinner() {
        Integer [] cout = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(getApplication(),android.R.layout.simple_spinner_dropdown_item,cout);
        spinner.setBackgroundColor(R.color.colorAccent);
        spinner.setAdapter(arrayAdapter);

    }

    private void getDataIntent() {
        final Intent intent = getIntent();
        final Product product;
       // final Product product ;
        product = (Product) intent.getSerializableExtra("dataproduct");
        mId          = product.getMId();
        mName        = product.getMName();
        mPrice       = product.getMPrice();
        mDescription = product.getMDescription();
        mPicture     = product.getMPicture();
        idProdutct   = product.getMIdProduct();
        txtName.setText(mName);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtPrice.setText("Giá : " + decimalFormat.format(mPrice) + " Đ ");
        txtDescription.setText(mDescription);
        Picasso.with(getApplicationContext()).load(mPicture).into(imgPicture);

        imgPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DetailProductActivity.this,GestureIMGActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("img",product);
                intent1.putExtra("data",bundle);
                startActivity(intent1);
//                Toast.makeText(DetailProductActivity.this, product.getMName()+ "", Toast.LENGTH_SHORT).show();
          }
        });


        Toast.makeText(this,product.getMName() + "", Toast.LENGTH_SHORT).show();
    }




    private void actionToolbar() {
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
        toolbar        = findViewById(R.id.toolbarDetailProduct);
        spinner        =  findViewById(R.id.spinnerPriceDetailProduct);
        btnClick       = findViewById(R.id.btnPriceDetailProduct);
        txtName        = findViewById(R.id.txtNameDetailProduct);
        txtPrice       = findViewById(R.id.txtPriceDetailProduct);
        txtDescription = findViewById(R.id.txtDescriptionDetailsProduct);
        imgPicture     = findViewById(R.id.imgDetailProduct);

    }
}
