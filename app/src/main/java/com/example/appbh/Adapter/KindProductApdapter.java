package com.example.appbh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbh.Model.KindProduct;
import com.example.appbh.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class KindProductApdapter extends BaseAdapter {
    Context context;
    ArrayList<KindProduct> arrayProduct;

    public KindProductApdapter(Context context, ArrayList<KindProduct> arrayProduct) {
        this.context = context;
        this.arrayProduct = arrayProduct;
    }

    @Override
    public int getCount() {
        return arrayProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayProduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        TextView txtTypeProduct;
        ImageView imgTypeProduct;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_listview_kindproduct,null);

            viewHolder.txtTypeProduct = convertView.findViewById(R.id.txtTypeProduct);
            viewHolder.imgTypeProduct = convertView.findViewById(R.id.imgTypeProduct);

            convertView.setTag(viewHolder); // gan vao layout
        }else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        KindProduct kindProduct = (KindProduct) getItem(position);
        viewHolder.txtTypeProduct.setText(kindProduct.getNameTypeProduct());
        Picasso.with(context).load(kindProduct.getPictureTypeProduct()).into(viewHolder.imgTypeProduct);

        return convertView;
    }
}
