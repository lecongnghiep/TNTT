package com.example.appbh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbh.Activity.DetailProductActivity;
import com.example.appbh.Model.Product;
import com.example.appbh.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductAdapter.ViewHolder> {
    Context context;
    ArrayList<Product> arraySearch;

    public SearchProductAdapter(Context context, ArrayList<Product> arraySearch) {
        this.context = context;
        this.arraySearch = arraySearch;
    }

    @NonNull
    @Override
    public SearchProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_search,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchProductAdapter.ViewHolder holder, int position) {
        Product product = arraySearch.get(position);
        holder.txtNameSearch.setText(product.getMName());
        holder.txtDescriptionSearch.setText(product.getMDescription());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtPriceSearch.setText("Giá : " + decimalFormat.format(product.getMPrice())+ "Đ");
        Picasso.with(context).load(product.getMPicture()).into(holder.imgProductSearch);
    }

    @Override
    public int getItemCount() {
        return arraySearch.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameSearch,txtPriceSearch,txtDescriptionSearch;
        ImageView imgProductSearch;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProductSearch = itemView.findViewById(R.id.imgProductSearch);
            txtNameSearch    = itemView.findViewById(R.id.txtNameSearch);
            txtPriceSearch   = itemView.findViewById(R.id.txtPriceSearch);
            txtDescriptionSearch = itemView.findViewById(R.id.txtDescriptionSearch);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailProductActivity.class);
                    intent.putExtra("dataproduct",arraySearch.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
