package com.example.crud;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder>{
    Context context;
    ArrayList order_id, order_date, order_quantity, order_totalmoney;

    HistoryAdapter(Context context,
                       ArrayList order_id,
                       ArrayList order_date,
                       ArrayList order_quantity,
                       ArrayList order_totalmoney){
        this.context = context;
        this.order_id = order_id;
        this.order_date = order_date;
        this.order_quantity = order_quantity;
        this.order_totalmoney = order_totalmoney;
    }
    @NonNull
    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_order_detail_history, parent, false);
        return new HistoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyViewHolder holder, int position) {
        holder.orderId_txt.setText(String.valueOf(order_id.get(position))) ;
        holder.order_date_txt.setText(String.valueOf(order_date.get(position)));
        holder.order_quantity_txt.setText(String.valueOf(order_quantity.get(position)));
        holder.order_totalmoney_txt.setText(String.valueOf(order_totalmoney.get(position)));
        holder.view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewOrderDetailHistory.orderId = Integer.valueOf(String.valueOf(order_id.get(position)));
                Intent intentEdit = new Intent(context, ViewOrderDetailHistory.class);
                context.startActivity(intentEdit);
            }
        });
    }

    @Override
    public int getItemCount() {
        return order_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView orderId_txt,order_date_txt,order_quantity_txt,order_totalmoney_txt;
        Button view_btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            orderId_txt = itemView.findViewById(R.id.order_id_txt);
            order_date_txt = itemView.findViewById(R.id.order_date_txt);
            order_quantity_txt = itemView.findViewById(R.id.order_quantity_txt);
            order_totalmoney_txt = itemView.findViewById(R.id.order_total_txt);
            view_btn = itemView.findViewById(R.id.viewBtn);
        }
    }
}
