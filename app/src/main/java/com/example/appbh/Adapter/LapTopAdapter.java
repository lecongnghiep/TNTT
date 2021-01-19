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

public class LapTopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrayProductLapTop;

    public LapTopAdapter(Context context, ArrayList<Product> arrayProductLapTop) {
        this.context = context;
        this.arrayProductLapTop = arrayProductLapTop;
    }

    @Override
    public int getCount() {
        return arrayProductLapTop.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayProductLapTop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        TextView txtNameLaptop,txtPriceLaptop,txtDescriptionLaptop;
        ImageView imgProductLaptop;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LapTopAdapter.ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_laptop,null);

            viewHolder.txtNameLaptop = convertView.findViewById(R.id.txtNameLaptop);
            viewHolder.txtPriceLaptop = convertView.findViewById(R.id.txtPriceLaptop);
            viewHolder.txtDescriptionLaptop = convertView.findViewById(R.id.txtDescriptionLaptop);
            viewHolder.imgProductLaptop = convertView.findViewById(R.id.imgProductLaptop);



            convertView.setTag(viewHolder); // gan vao layout
        }else {
            viewHolder = (LapTopAdapter.ViewHolder) convertView.getTag();

        }
        Product product = (Product) getItem(position);
        viewHolder.txtNameLaptop.setText(product.getMName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPriceLaptop.setText("Giá : " + decimalFormat.format(product.getMPrice())+ "Đ");

        viewHolder.txtDescriptionLaptop.setText(product.getMDescription());
        viewHolder.txtDescriptionLaptop.setMaxLines(3);
        viewHolder.txtDescriptionLaptop.setEllipsize(TextUtils.TruncateAt.END);

        Picasso.with(context).load(product.getMPicture()).into(viewHolder.imgProductLaptop);
        //gán animation
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.custom_ani_view);
        convertView.startAnimation(animation);

        return convertView;
    }
}
