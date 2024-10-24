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

import java.util.ArrayList;
import java.util.List;

public class CardActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    ArrayList<String> orderdetail_id, orderdetail_product_id, orderdetail_quantity;
    OrderDetailAdapter orderDetailAdapter;
    public static int orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card);
        recyclerView = findViewById(R.id.recycleView);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        myDB = new MyDatabaseHelper(CardActivity.this);
        orderdetail_id = new ArrayList<>();
        orderdetail_product_id = new ArrayList<>();
        orderdetail_quantity = new ArrayList<>();

        storedataInArray();
        orderDetailAdapter = new OrderDetailAdapter(CardActivity.this, orderdetail_id,orderdetail_product_id,orderdetail_quantity);
        recyclerView.setAdapter(orderDetailAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(CardActivity.this));
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
            Intent intentEdit = new Intent(CardActivity.this, CardActivity.class);
            startActivity(intentEdit);
        }

        return super.onOptionsItemSelected(item);
    }
    private void logout() {
        // Xử lý việc logout, ví dụ: xóa thông tin đăng nhập đã lưu (nếu có)

        Intent intent = new Intent(CardActivity.this, Login.class);
        startActivity(intent);
        finish();

        Toast.makeText(this, "Bạn đã đăng xuất!", Toast.LENGTH_SHORT).show();
    }
    void storedataInArray() {
        Cursor cursor = myDB.getOrderDetailsByOrderId(orderId);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_LONG).show();
        } else {
            orderdetail_id.clear();
            orderdetail_product_id.clear();
            orderdetail_quantity.clear();

            while (cursor.moveToNext()) {
                int orderDetailIdColumnIndex = cursor.getColumnIndex("orderDetail_id");
                int productIdColumnIndex = cursor.getColumnIndex("orderDetail_product_id");
                int quantityColumnIndex = cursor.getColumnIndex("orderDetail_order_quantity");

                if (orderDetailIdColumnIndex != -1 && productIdColumnIndex != -1 && quantityColumnIndex != -1) {
                    String orderDetailId = cursor.getString(orderDetailIdColumnIndex);
                    String productId = cursor.getString(productIdColumnIndex);
                    int quantity = cursor.getInt(quantityColumnIndex);

                    orderdetail_id.add(orderDetailId);
                    orderdetail_product_id.add(productId);
                    orderdetail_quantity.add(String.valueOf(quantity));
                }
            }
        }
        cursor.close();
    }
}
