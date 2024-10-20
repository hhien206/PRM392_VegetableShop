package com.example.crud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    }

    @Override
    public int getItemCount() {
        return vegetable_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView vegetable_id_txt,vegetable_name_txt,vegetable_category_txt,vegetable_origincountry_txt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            vegetable_id_txt = itemView.findViewById(R.id.vegetable_id_txt);
            vegetable_name_txt = itemView.findViewById(R.id.vegetable_name_txt);
            vegetable_category_txt = itemView.findViewById(R.id.vegetable_category_txt);
            vegetable_origincountry_txt = itemView.findViewById(R.id.vegetable_origincountry_txt);
        }
    }
}
