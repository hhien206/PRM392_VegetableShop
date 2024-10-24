package com.example.crud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder> {
    Context context;
    ArrayList orderdetail_id, orderdetail_product_id, orderdetail_quantity;

    OrderDetailAdapter(Context context,
                       ArrayList orderdetail_id,
                       ArrayList orderdetail_product_id,
                       ArrayList orderdetail_quantity){
        this.context = context;
        this.orderdetail_id = orderdetail_id;
        this.orderdetail_product_id = orderdetail_product_id;
        this.orderdetail_quantity = orderdetail_quantity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_detail, parent, false);
        return new OrderDetailAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.orderDetailId_txt.setText(String.valueOf(orderdetail_id.get(position)));
        holder.productId_txt.setText(String.valueOf(orderdetail_product_id.get(position)));
        holder.quaitity_txt.setText(String.valueOf(orderdetail_quantity.get(position)));
    }

    @Override
    public int getItemCount() {
        return orderdetail_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView orderDetailId_txt,productId_txt,quaitity_txt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            orderDetailId_txt = itemView.findViewById(R.id.orderDetailId_txt);
            productId_txt = itemView.findViewById(R.id.productId_txt);
            quaitity_txt = itemView.findViewById(R.id.quaitity_txt);
        }
    }
}
