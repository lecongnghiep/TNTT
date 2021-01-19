package com.example.appbh.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbh.Model.Product;
import com.example.appbh.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PhoneAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrayProductPhone;

    public PhoneAdapter(Context context, ArrayList<Product> arrayProductPhone) {
        this.context = context;
        this.arrayProductPhone = arrayProductPhone;
    }

    @Override
    public int getCount() {
        return arrayProductPhone.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayProductPhone.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        TextView txtNamePhone,txtPricePhone,txtDescriptionPhone;
        ImageView imgProductPhone;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_phone,null);

            viewHolder.txtNamePhone = convertView.findViewById(R.id.txtNamePhone);
            viewHolder.txtPricePhone = convertView.findViewById(R.id.txtPricePhone);
            viewHolder.txtDescriptionPhone = convertView.findViewById(R.id.txtDescriptionPhone);
            viewHolder.imgProductPhone = convertView.findViewById(R.id.imgProductPhone);



            convertView.setTag(viewHolder); // gan vao layout
        }else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        Product product = (Product) getItem(position);
        viewHolder.txtNamePhone.setText(product.getMName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPricePhone.setText("Giá : " + decimalFormat.format(product.getMPrice())+ "Đ");

        viewHolder.txtDescriptionPhone.setText(product.getMDescription());
        viewHolder.txtDescriptionPhone.setMaxLines(3);
        viewHolder.txtDescriptionPhone.setEllipsize(TextUtils.TruncateAt.END);

        Picasso.with(context).load(product.getMPicture()).into(viewHolder.imgProductPhone);
        //gán animation
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.custom_ani_view);
        convertView.startAnimation(animation);

        return convertView;
    }
}
