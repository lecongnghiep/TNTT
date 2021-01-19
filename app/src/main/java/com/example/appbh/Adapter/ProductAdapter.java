package com.example.appbh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbh.Activity.DetailProductActivity;
import com.example.appbh.Model.Product;
import com.example.appbh.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    ArrayList<Product> arrayProduct;
    Context context ;

    public ProductAdapter(ArrayList<Product> arrayProduct, Context context) {
        this.arrayProduct = arrayProduct;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_product,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = arrayProduct.get(position);
        holder.txtName.setText(product.getMName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtPrice.setText("Giá : " + decimalFormat.format(product.getMPrice())+ "Đ");
        Picasso.with(context).load(product.getMPicture()).into(holder.imgPicture);




    }

    @Override
    public int getItemCount() {
        return arrayProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPicture;
        TextView txtName,txtPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPicture = itemView.findViewById(R.id.imgProduct);
            txtName    = itemView.findViewById(R.id.txtNameProduct);
            txtPrice   = itemView.findViewById(R.id.txtPriceProduct);
            //gán animation
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.custom_ani_view);
            itemView.startAnimation(animation);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailProductActivity.class);
                    intent.putExtra("dataproduct",arrayProduct.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

        }
    }
}
