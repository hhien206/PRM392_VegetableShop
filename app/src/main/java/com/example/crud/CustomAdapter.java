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

import com.example.crud.model.Order;
import com.example.crud.model.OrderDetail;
import com.example.crud.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    Context context;
    ArrayList vegetable_id, vegetable_name, vegetable_category, vegetable_origincountry,vegetable_price;

    CustomAdapter(Context context,
                  ArrayList vegetable_id,
                  ArrayList vegetable_name,
                  ArrayList vegetable_category,
                  ArrayList vegetable_origincountry,
                  ArrayList vegetable_price) {
        this.context = context;
        this.vegetable_id = vegetable_id;
        this.vegetable_name = vegetable_name;
        this.vegetable_category = vegetable_category;
        this.vegetable_origincountry = vegetable_origincountry;
        this.vegetable_price = vegetable_price;
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
        holder.vegetable_price_txt.setText(String.valueOf(vegetable_price.get(position)));

        holder.addCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper databaseHelper = new MyDatabaseHelper(context);
                Cursor cursor1 = databaseHelper.getCartByUserId(Login.account_Id);
                Order order = new Order();
                order = Order.ConvertCursorIntoOrder(cursor1);
                if(order == null){
                    long id = databaseHelper.addOrder(String.valueOf(Login.account_Id), null, null, null, "CART");
                    databaseHelper.addOrderDetail(String.valueOf(id), holder.vegetable_id_txt.getText().toString(), String.valueOf(1), holder.vegetable_price_txt.getText().toString());
                    CardActivity.orderId = (int)id;
                }else{
                    int orderId = order.getId();
                    Cursor cursor2 = databaseHelper.getOrderDetailsByOrderId(orderId);
                    List<OrderDetail> orderDetails = new ArrayList<>();
                    orderDetails = OrderDetail.ConvertCursorIntoListOrderDetail(cursor2);
                    boolean found = false;
                    for (OrderDetail item: orderDetails) {
                        String orderDetailId = String.valueOf(item.getId());
                        int quantity = item.getQuantity();
                        double totalMoney = (quantity + 1) * Double.valueOf(holder.vegetable_price_txt.getText().toString());
                        if (String.valueOf(item.getProductId()).equals(holder.vegetable_id_txt.getText().toString())) {
                            databaseHelper.updateOrderDetail(
                                    orderDetailId,
                                    String.valueOf(  quantity+ 1),
                                    String.valueOf(totalMoney)
                            );
                            found = true;
                            break;
                        }
                    }
                    if(!found){
                        databaseHelper.addOrderDetail(String.valueOf(CardActivity.orderId), holder.vegetable_id_txt.getText().toString(), String.valueOf(1), holder.vegetable_price_txt.getText().toString());
                    }
                }
                Toast.makeText(context, "Add vegetable success!", Toast.LENGTH_LONG).show();
            }
        });

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
                                    holder.vegetable_origincountry_txt.getText().toString(),
                                    holder.vegetable_price_txt.getText().toString());
                            Intent intentEdit = new Intent(context, UpdateActivity.class);
                            context.startActivity(intentEdit);
                            return true;
                        } else if (itemId == R.id.deleteBtn) {
                            MyDatabaseHelper databaseHelper = new MyDatabaseHelper(context);
                            databaseHelper.deleteVegetable(holder.vegetable_id_txt.getText().toString());
                            Intent intentDelete = new Intent(context, MainActivity.class);
                            context.startActivity(intentDelete);
                            return true;
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
        TextView vegetable_id_txt, vegetable_name_txt, vegetable_category_txt, vegetable_origincountry_txt, vegetable_price_txt;
        ImageButton menuButton;
        FloatingActionButton addCardBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            vegetable_id_txt = itemView.findViewById(R.id.vegetable_id_txt);
            vegetable_name_txt = itemView.findViewById(R.id.vegetable_name_txt);
            vegetable_category_txt = itemView.findViewById(R.id.vegetable_category_txt);
            vegetable_origincountry_txt = itemView.findViewById(R.id.vegetable_origincountry_txt);
            vegetable_price_txt = itemView.findViewById(R.id.vegetable_price_txt);
            menuButton = itemView.findViewById(R.id.menuButton);
            addCardBtn = itemView.findViewById(R.id.addCardBtn);

            if(Login.account_role.equals("Admin")){
                menuButton.setVisibility(View.VISIBLE);
            }
            else{
                menuButton.setVisibility(View.GONE);
            }
            if(Login.account_role.equals("Admin")){
                addCardBtn.setVisibility(View.GONE);
            }
            else{
                addCardBtn.setVisibility(View.VISIBLE);
            }
        }
    }
}
