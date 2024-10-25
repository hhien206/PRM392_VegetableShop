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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    ArrayList<String> order_id, order_date, order_quantity, order_totalmoney;
    HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_history);
        recyclerView = findViewById(R.id.recycleView1);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        myDB = new MyDatabaseHelper(HistoryActivity.this);
        order_id = new ArrayList<>();
        order_date = new ArrayList<>();
        order_quantity = new ArrayList<>();
        order_totalmoney= new ArrayList<>();

        storedataInArray();
        historyAdapter = new HistoryAdapter(HistoryActivity.this, order_id,order_date,order_quantity,order_totalmoney);
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
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
            Intent intentEdit = new Intent(HistoryActivity.this, CardActivity.class);
            startActivity(intentEdit);
        }

        return super.onOptionsItemSelected(item);
    }
    private void logout() {
        // Xử lý việc logout, ví dụ: xóa thông tin đăng nhập đã lưu (nếu có)

        Intent intent = new Intent(HistoryActivity.this, Login.class);
        startActivity(intent);
        finish();

        Toast.makeText(this, "Bạn đã đăng xuất!", Toast.LENGTH_SHORT).show();
    }
    void storedataInArray() {
        Cursor cursor = myDB.getCompletedOrdersByUserId(Login.account_Id);
        if(cursor == null) return;;
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_LONG).show();
        } else {
            order_id.clear();
            order_date.clear();
            order_quantity.clear();
            order_totalmoney.clear();

            while (cursor.moveToNext()) {
                int orderIdColumnIndex = cursor.getColumnIndex("order_id");
                int orderDateColumnIndex = cursor.getColumnIndex("order_date");
                int orderQuantityColumnIndex = cursor.getColumnIndex("order_quantity");
                int orderTotalMoneyColumnIndex = cursor.getColumnIndex("order_totalmoney");

                if (orderIdColumnIndex != -1 && orderDateColumnIndex != -1 && orderQuantityColumnIndex != -1 && orderTotalMoneyColumnIndex != -1) {
                    String orderId = cursor.getString(orderIdColumnIndex);
                    String orderDate = cursor.getString(orderDateColumnIndex);
                    int quantity = cursor.getInt(orderQuantityColumnIndex);
                    double totalMoney = cursor.getDouble(orderTotalMoneyColumnIndex);

                    order_id.add(orderId);
                    order_date.add(orderDate);
                    order_quantity.add(String.valueOf(quantity));
                    order_totalmoney.add(String.valueOf(totalMoney));
                }
            }
        }
        cursor.close();
    }
}
