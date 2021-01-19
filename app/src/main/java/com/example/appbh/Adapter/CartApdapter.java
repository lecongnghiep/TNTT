package com.example.appbh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;
import com.example.appbh.Activity.CartActivity;
import com.example.appbh.Activity.MainActivity;
import com.example.appbh.Model.Cart;
import com.example.appbh.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartApdapter extends BaseAdapter {
    Context context ;
    ArrayList<Cart> arrayCart;

    public CartApdapter(Context context, ArrayList<Cart> arrayCart) {
        this.context = context;
        this.arrayCart = arrayCart;
    }

    @Override
    public int getCount() {
        return arrayCart.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayCart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView txtNameCartItem,txtPriceCartItem;
        ImageView imgCartItem;
        Button btnMinusCartItem, btnPlusCartItem,btnValueCartItem;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       ViewHolder viewHolder = null;
        if(viewHolder == null){
            viewHolder = new ViewHolder() ;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_cart,null);
            viewHolder.txtNameCartItem = convertView.findViewById(R.id.txtNameCartItem);
            viewHolder.txtPriceCartItem = convertView.findViewById(R.id.txtPriceNewCartItem);
            viewHolder.imgCartItem  = convertView.findViewById(R.id.imgCartItem);
            viewHolder.btnMinusCartItem = convertView.findViewById(R.id.btnMinCartItem);
            viewHolder.btnPlusCartItem = convertView.findViewById(R.id.btnMaxCartItem);
            viewHolder.btnValueCartItem = convertView.findViewById(R.id.btnValueCartItem);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Cart cart = (Cart) getItem(position);
        viewHolder.txtNameCartItem.setText(cart.getmName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPriceCartItem.setText("Giá : " + decimalFormat.format(cart.getmPrice()) + "Đ" );
        Picasso.with(context).load(cart.getmPicture()).into(viewHolder.imgCartItem);
        viewHolder.btnValueCartItem.setText(cart.getmAmount() + "");

        final ViewHolder finalViewHolder = viewHolder;
        String sl1 = finalViewHolder.btnValueCartItem.getText().toString();
      final int sl = Integer.parseInt(sl1);
        if(sl >= 10){
            finalViewHolder.btnPlusCartItem.setVisibility(View.INVISIBLE);
        }else if(sl <= 1){
            finalViewHolder.btnMinusCartItem.setVisibility(View.INVISIBLE);
        }else {
            finalViewHolder.btnPlusCartItem.setVisibility(View.VISIBLE);
            finalViewHolder.btnPlusCartItem.setVisibility(View.VISIBLE);
        }



        viewHolder.btnPlusCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slNew = Integer.parseInt(finalViewHolder.btnValueCartItem.getText().toString()) + 1;
                int slSimple = MainActivity.arrayCart.get(position).getmAmount();
                long mPrice = MainActivity.arrayCart.get(position).getmPrice();
                MainActivity.arrayCart.get(position).setmAmount(slNew);
                long priceNew = (slNew * mPrice)/ slSimple;
                MainActivity.arrayCart.get(position).setmPrice(priceNew);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtPriceCartItem.setText("Giá : " + decimalFormat.format(priceNew) + "Đ" );
                CartActivity.eventCart();
                if(slNew > 9){
                    finalViewHolder.btnPlusCartItem.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnMinusCartItem.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValueCartItem.setText(String.valueOf(slNew));
                }else {
                    finalViewHolder.btnPlusCartItem.setVisibility(View.VISIBLE);
                    finalViewHolder.btnMinusCartItem.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValueCartItem.setText(String.valueOf(slNew));
                }
            }
        });

        finalViewHolder.btnMinusCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slNew = Integer.parseInt(finalViewHolder.btnValueCartItem.getText().toString()) - 1;
                int slSimple = MainActivity.arrayCart.get(position).getmAmount();
                long mPrice = MainActivity.arrayCart.get(position).getmPrice();
                MainActivity.arrayCart.get(position).setmAmount(slNew);
                long priceNew = (slNew * mPrice)/ slSimple;
                MainActivity.arrayCart.get(position).setmPrice(priceNew);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtPriceCartItem.setText("Giá : " + decimalFormat.format(priceNew) + "Đ" );
                CartActivity.eventCart();
                if(slNew < 2){
                    finalViewHolder.btnPlusCartItem.setVisibility(View.VISIBLE);
                    finalViewHolder.btnMinusCartItem.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnValueCartItem.setText(String.valueOf(slNew));
                }else {
                    finalViewHolder.btnPlusCartItem.setVisibility(View.VISIBLE);
                    finalViewHolder.btnMinusCartItem.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValueCartItem.setText(String.valueOf(slNew));
                }
            }
        });

        return convertView;
    }
}
