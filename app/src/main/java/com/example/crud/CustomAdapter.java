package com.example.crud;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    Context context;
    ArrayList vegetable_id, vegetable_name, vegetable_category, vegetable_origincountry;

    CustomAdapter(Context context,
                  ArrayList vegetable_id,
                  ArrayList vegetable_name,
                  ArrayList vegetable_category,
                  ArrayList vegetable_origincountry) {
        this.context = context;
        this.vegetable_id = vegetable_id;
        this.vegetable_name = vegetable_name;
        this.vegetable_category = vegetable_category;
        this.vegetable_origincountry = vegetable_origincountry;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.vegetable_id_txt.setText(String.valueOf(vegetable_id.get(position)));
        holder.vegetable_name_txt.setText(String.valueOf(vegetable_name.get(position)));
        holder.vegetable_category_txt.setText(String.valueOf(vegetable_category.get(position)));
        holder.vegetable_origincountry_txt.setText(String.valueOf(vegetable_origincountry.get(position)));

        holder.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.menuButton);
                popupMenu.getMenuInflater().inflate(R.menu.vegetable_item_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId(); // Lưu ID của mục

                        if (itemId == R.id.editBtn) {
                            UpdateActivity.initialValues(holder.vegetable_id_txt.getText().toString(),
                                    holder.vegetable_name_txt.getText().toString(),
                                    holder.vegetable_category_txt.getText().toString(),
                                    holder.vegetable_origincountry_txt.getText().toString());
                            Intent intentEdit = new Intent(context, UpdateActivity.class);
                            context.startActivity(intentEdit);
                            return true;
                        } else if (itemId == R.id.deleteBtn) {
                            MyDatabaseHelper databaseHelper = new MyDatabaseHelper(context);
                            databaseHelper.deleteVegetable(holder.vegetable_id_txt.getText().toString());
                            Intent intentDelete = new Intent(context, MainActivity.class);
                            context.startActivity(intentDelete);
                            return true;
                        } else if(itemId == R.id.addCardBtn){
                            MyDatabaseHelper databaseHelper = new MyDatabaseHelper(context);
                            Cursor cursor1 = databaseHelper.getOrderByUserId(Login.account_Id);
                            if(cursor1 == null){
                                long id = databaseHelper.addOrder(String.valueOf(Login.account_Id),null,null,null,null);
                                databaseHelper.addOrderDetail(String.valueOf(id),holder.vegetable_id_txt.getText().toString(),String.valueOf(1),null);
                            }else{
                                List<Cursor> orderDetails = new ArrayList<>();
                                orderDetails = databaseHelper.GetOrderDetailsByOrderId(cursor1.getInt(cursor1.getColumnIndexOrThrow("COLUMN_ORDERDETAIL_ORDER_ID")));
                                for (Cursor orderDetail : orderDetails) {
                                    if(orderDetail.getString(orderDetail.getColumnIndexOrThrow("COLUMN_ORDERDETAIL_PRODUCT_ID")).equals(holder.vegetable_id_txt.getText().toString())){
                                        databaseHelper.updateOrderDetail(orderDetail.getString(orderDetail.getColumnIndexOrThrow("COLUMN_ORDERDETAIL_PRODUCT_ID")),
                                                String.valueOf(orderDetail.getInt(orderDetail.getColumnIndexOrThrow("COLUMN_ORDERDETAIL_QUANTITY")) + 1),null);
                                    }

                                    if(orderDetail == orderDetails.get((int)orderDetails.stream().count())){
                                        databaseHelper.addOrderDetail(cursor1.getString(cursor1.getColumnIndexOrThrow("COLUMN_ORDERDETAIL_ORDER_ID")),holder.vegetable_id_txt.getText().toString(),String.valueOf(1),null);
                                    }
                                }
                            }

                            Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return vegetable_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView vegetable_id_txt, vegetable_name_txt, vegetable_category_txt, vegetable_origincountry_txt;
        ImageButton menuButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            vegetable_id_txt = itemView.findViewById(R.id.vegetable_id_txt);
            vegetable_name_txt = itemView.findViewById(R.id.vegetable_name_txt);
            vegetable_category_txt = itemView.findViewById(R.id.vegetable_category_txt);
            vegetable_origincountry_txt = itemView.findViewById(R.id.vegetable_origincountry_txt);
            menuButton = itemView.findViewById(R.id.menuButton);
        }
    }
}
