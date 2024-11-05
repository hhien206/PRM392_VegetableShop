package com.example.crud;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud.model.Vegetable;

import java.util.ArrayList;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder> {
    Context context;
    ArrayList orderdetail_id, orderdetail_product_id, orderdetail_quantity, orderdetail_total_money;

    OrderDetailAdapter(Context context,
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_detail, parent, false);
        return new OrderDetailAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyDatabaseHelper myDB = new MyDatabaseHelper(context);
        Cursor cursor1 = myDB.getVegetableById(Integer.valueOf(String.valueOf(orderdetail_product_id.get(position))));
        Vegetable vegetable = new Vegetable();
        vegetable = Vegetable.ConvertCursorIntoVegetable(cursor1);
        if(vegetable != null){
            holder.productId_txt.setText(vegetable.getName());
        }
        holder.orderDetailId_txt.setText(String.valueOf(orderdetail_id.get(position)));
        holder.quaitity_txt.setText(String.valueOf(orderdetail_quantity.get(position)));
        holder.total_money_txt.setText(String.valueOf(orderdetail_total_money.get(position)));
        holder.remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB.deleteOrderDetail(String.valueOf(holder.orderDetailId_txt.getText()));
                Intent intentEdit = new Intent(context, CardActivity.class);
                context.startActivity(intentEdit);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderdetail_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView orderDetailId_txt,productId_txt,quaitity_txt, total_money_txt;
        Button remove_btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            orderDetailId_txt = itemView.findViewById(R.id.orderDetailId_txt);
            productId_txt = itemView.findViewById(R.id.productId_txt);
            quaitity_txt = itemView.findViewById(R.id.quaitity_txt);
            total_money_txt = itemView.findViewById(R.id.total_money_txt);
            remove_btn = itemView.findViewById(R.id.removeBtn);
        }
    }
}
