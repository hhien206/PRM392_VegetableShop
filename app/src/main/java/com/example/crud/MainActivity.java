package com.example.crud;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    MyDatabaseHelper myDB;
    ArrayList<String> vegetable_id, vegetable_name,vegetable_category,vegetable_origincountry;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycleView);
        add_button = findViewById(R.id.add_button);
        if(Login.account_role.equals("Admin")){
            add_button.setVisibility(View.VISIBLE);
        }
        else{
            add_button.setVisibility(View.GONE);
        }
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        myDB = new MyDatabaseHelper(MainActivity.this);
        vegetable_id = new ArrayList<>();
        vegetable_name = new ArrayList<>();
        vegetable_category = new ArrayList<>();
        vegetable_origincountry = new ArrayList<>();

        storedataInArray();
        customAdapter = new CustomAdapter(MainActivity.this, vegetable_id,vegetable_name,vegetable_category,vegetable_origincountry);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }
    void storedataInArray(){
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data",Toast.LENGTH_LONG).show();
        }else {
            while (cursor.moveToNext()){
                vegetable_id.add(cursor.getString(0));
                vegetable_name.add(cursor.getString(1));
                vegetable_category.add(cursor.getString(2));
                vegetable_origincountry.add(cursor.getString(3));
            }
        }
    }
}