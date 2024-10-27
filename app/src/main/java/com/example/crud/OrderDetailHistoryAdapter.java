package com.example.crud;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderDetailHistoryAdapter extends RecyclerView.Adapter<OrderDetailHistoryAdapter.MyViewHolder> {
    Context context;
    ArrayList orderdetail_id, orderdetail_product_id, orderdetail_quantity, orderdetail_total_money;
    OrderDetailHistoryAdapter(Context context,
                       ArrayList orderdetail_id,
                       ArrayList orderdetail_product_id,
                       ArrayList orderdetail_quantity,
                       ArrayList orderdetail_total_money){
        this.context = context;
        this.orderdetail_id = orderdetail_id;
        this.orderdetail_product_id = orderdetail_product_id;
        this.orderdetail_quantity = orderdetail_quantity;
        this.orderdetail_total_money = orderdetail_total_money;
    }
    @NonNull
    @Override
    public OrderDetailHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_order_detail_history_card_real, parent, false);
        return new OrderDetailHistoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailHistoryAdapter.MyViewHolder holder, int position) {
        MyDatabaseHelper myDB = new MyDatabaseHelper(context);
        Cursor cursor1 = myDB.getVegetableById(Integer.valueOf(String.valueOf(orderdetail_product_id.get(position))));
        if(cursor1 != null){
            if (cursor1.moveToFirst()) {
                int productNameColumnIndex = cursor1.getColumnIndex("vegetable_name");
                if (productNameColumnIndex != -1) {
                    holder.productId_txt.setText(String.valueOf(cursor1.getString(productNameColumnIndex)));
                }
            }
        }
        holder.orderDetailId_txt.setText(String.valueOf(orderdetail_id.get(position)));
        holder.quaitity_txt.setText(String.valueOf(orderdetail_quantity.get(position)));
        holder.total_money_txt.setText(String.valueOf(orderdetail_total_money.get(position)));
    }

    @Override
    public int getItemCount() {
        return orderdetail_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView orderDetailId_txt,productId_txt,quaitity_txt, total_money_txt;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            orderDetailId_txt = itemView.findViewById(R.id.orderDetailId_txt);
            productId_txt = itemView.findViewById(R.id.productId_txt);
            quaitity_txt = itemView.findViewById(R.id.quaitity_txt);
            total_money_txt = itemView.findViewById(R.id.total_money_txt);
        }
    }
}
