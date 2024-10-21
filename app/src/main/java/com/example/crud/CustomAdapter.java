package com.example.crud;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    Context context;
    ArrayList vegetable_id,vegetable_name, vegetable_category, vegetable_origincountry;
    CustomAdapter(Context context,
                  ArrayList vegetable_id,
                  ArrayList vegetable_name,
                  ArrayList vegetable_category,
                  ArrayList vegetable_origincountry){
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
        View view  = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.vegetable_id_txt.setText(String.valueOf(vegetable_id.get(position)));
        holder.vegetable_name_txt.setText(String.valueOf(vegetable_name.get(position)));
        holder.vegetable_category_txt.setText(String.valueOf(vegetable_category.get(position)));
        holder.vegetable_origincountry_txt.setText(String.valueOf(vegetable_origincountry.get(position)));
        holder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateActivity.initialValues(holder.vegetable_id_txt.getText().toString(),
                        holder.vegetable_name_txt.getText().toString(),
                        holder.vegetable_category_txt.getText().toString(),
                        holder.vegetable_origincountry_txt.getText().toString());
                Intent intent = new Intent(context, UpdateActivity.class);
                context.startActivity(intent);
            }
        });
        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper databaseHelper = new MyDatabaseHelper(context);
                databaseHelper.deleteVegetable(holder.vegetable_id_txt.getText().toString());
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vegetable_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView vegetable_id_txt,vegetable_name_txt,vegetable_category_txt,vegetable_origincountry_txt;
        Button edit_btn,delete_btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            vegetable_id_txt = itemView.findViewById(R.id.vegetable_id_txt);
            vegetable_name_txt = itemView.findViewById(R.id.vegetable_name_txt);
            vegetable_category_txt = itemView.findViewById(R.id.vegetable_category_txt);
            vegetable_origincountry_txt = itemView.findViewById(R.id.vegetable_origincountry_txt);
            edit_btn = itemView.findViewById(R.id.editBtn);
            delete_btn = itemView.findViewById(R.id.deleteBtn);
            if(Login.account_role.equals("Admin")){
                edit_btn.setVisibility(View.VISIBLE);
                delete_btn.setVisibility(View.VISIBLE);
            }
            else{
                edit_btn.setVisibility(View.GONE);
                delete_btn.setVisibility(View.GONE);
            }
        }
    }
}
