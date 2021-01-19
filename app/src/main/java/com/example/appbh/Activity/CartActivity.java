package com.example.appbh.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbh.Adapter.CartApdapter;
import com.example.appbh.R;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {
    ListView lsvCart;
    TextView txtNotify;
    static TextView txtPriceCart;
    Button btnPayCar,btnBackCart;
    Toolbar toolbar;
    CartApdapter apdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
        actionToolBar();
       checkDataCart();
        eventCart();
        onItemListView();
        eventButton();
    }

    private void eventButton() {
        btnBackCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        btnPayCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.arrayCart.size() > 0){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(CartActivity.this, "Giỏ Hàng Của Bạn Đang Trống", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onItemListView() {
        lsvCart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Dialog dialog = new Dialog(CartActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_delete);

                Button btnDelete   =  dialog.findViewById(R.id.btnDelete);
                Button btnCancle   = dialog.findViewById(R.id.btnCancle);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MainActivity.arrayCart.size() <= 0){
                            txtNotify.setVisibility(View.VISIBLE);

                        }else {
                            MainActivity.arrayCart.remove(position);
                            apdapter.notifyDataSetChanged();
                            eventCart();
                            if(MainActivity.arrayCart.size() <= 0) {
                                txtNotify.setVisibility(View.VISIBLE);
                            } else {
                                txtNotify.setVisibility(View.INVISIBLE);
                                apdapter.notifyDataSetChanged();
                                eventCart();
                            }
                        }
                        dialog.cancel();
                       Toast.makeText(CartActivity.this, "Bạn Đã Xóa Thành Công", Toast.LENGTH_SHORT).show();
                    }
                });
                btnCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();
//                final AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
//                builder.setTitle("Xác Nhận Xóa Sản Phẩm");
//                builder.setMessage("Bạn Có Chắc Là Không Mua Thằng Này Nữa ?");
//                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int i) {
//                        if(MainActivity.arrayCart.size() <= 0){
//                            txtNotify.setVisibility(View.VISIBLE);
//
//                        }else {
//                            MainActivity.arrayCart.remove(position);
//                            apdapter.notifyDataSetChanged();
//                            eventCart();
//                            if(MainActivity.arrayCart.size() <= 0) {
//                                txtNotify.setVisibility(View.VISIBLE);
//                            } else {
//                                txtNotify.setVisibility(View.INVISIBLE);
//                                apdapter.notifyDataSetChanged();
//                                eventCart();
//                            }
//                        }
//                    }
//                });
//                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        builder.setCancelable(true);
//                    }
//                });
//                builder.show();
                return true;
            }
        });
    }

    public static void eventCart() {
        long total = 0;
        for(int i = 0 ; i < MainActivity.arrayCart.size() ; i ++){
            total += MainActivity.arrayCart.get(i).getmPrice();
        }
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        txtPriceCart.setText(decimalFormat.format(total)+ "Đ");
    }

    private void checkDataCart() {
        if(MainActivity.arrayCart.size() <= 0){
            apdapter.notifyDataSetChanged();
            txtNotify.setVisibility(View.VISIBLE);
            lsvCart.setVisibility(View.INVISIBLE);
        }else {
            apdapter.notifyDataSetChanged();
            txtNotify.setVisibility(View.INVISIBLE);
            lsvCart.setVisibility(View.VISIBLE);
        }
    }

    private void actionToolBar() {
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
        lsvCart = findViewById(R.id.lsvCart);
        txtNotify = findViewById(R.id.txtCart);
        txtPriceCart = findViewById(R.id.txtPriceCart);
        btnBackCart = findViewById(R.id.btnBackCart);
        btnPayCar = findViewById(R.id.btnPayCart);
        toolbar = findViewById(R.id.toolbarCart);
        apdapter = new CartApdapter(CartActivity.this,MainActivity.arrayCart);
        lsvCart.setAdapter(apdapter);
    }
}
