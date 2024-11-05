package com.example.crud;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud.model.Vegetable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    MyDatabaseHelper myDB;
    ArrayList<String> vegetable_id, vegetable_name,vegetable_category,vegetable_origincountry, vegetable_price;
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
        vegetable_price = new ArrayList<>();

        storedataInArray();
        customAdapter = new CustomAdapter(MainActivity.this, vegetable_id,vegetable_name,vegetable_category,vegetable_origincountry,vegetable_price);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.vegetable_main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Xử lý sự kiện khi người dùng chọn các mục trong menu
        int id = item.getItemId();

        if (id == R.id.logout) {
            // Gọi hàm xử lý logout
            logout();
            return true;
        }else if(id == R.id.cart){
            Intent intentEdit = new Intent(MainActivity.this, CardActivity.class);
            startActivity(intentEdit);
        }else if(id== R.id.history){
            Intent intentEdit = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intentEdit);
        }

        return super.onOptionsItemSelected(item);
    }
    private void logout() {
        // Xử lý việc logout, ví dụ: xóa thông tin đăng nhập đã lưu (nếu có)

        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();

        Toast.makeText(this, "Bạn đã đăng xuất!", Toast.LENGTH_SHORT).show();
    }
    void storedataInArray(){
        Cursor cursor = myDB.readAllData();
        List<Vegetable> vegetables = new ArrayList<>();
        vegetables = Vegetable.ConvertCursorIntoListVegetable(cursor);
        for (Vegetable item : vegetables) {
            vegetable_id.add(String.valueOf(item.getId()));
            vegetable_name.add(String.valueOf(item.getName()));
            vegetable_category.add(String.valueOf(item.getCategory()));
            vegetable_origincountry.add(String.valueOf(item.getOriginCountry()));
            vegetable_price.add(String.valueOf(item.getPrice()));
        }
    }
}