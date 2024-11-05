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

import com.example.crud.model.OrderDetail;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewOrderDetailHistory extends AppCompatActivity {

    RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    ArrayList<String> orderdetail_id, orderdetail_product_id, orderdetail_quantity, orderdetail_total_money;
    OrderDetailHistoryAdapter orderDetailHistoryAdapter;
    public static int orderId = 0;

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
        myDB = new MyDatabaseHelper(ViewOrderDetailHistory.this);
        orderdetail_id = new ArrayList<>();
        orderdetail_product_id = new ArrayList<>();
        orderdetail_quantity = new ArrayList<>();
        orderdetail_total_money = new ArrayList<>();

        storedataInArray();
        orderDetailHistoryAdapter = new OrderDetailHistoryAdapter(ViewOrderDetailHistory.this, orderdetail_id,orderdetail_product_id,orderdetail_quantity,orderdetail_total_money);
        recyclerView.setAdapter(orderDetailHistoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewOrderDetailHistory.this));
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
            Intent intentEdit = new Intent(ViewOrderDetailHistory.this, CardActivity.class);
            startActivity(intentEdit);
        }

        return super.onOptionsItemSelected(item);
    }
    private void logout() {
        // Xử lý việc logout, ví dụ: xóa thông tin đăng nhập đã lưu (nếu có)

        Intent intent = new Intent(ViewOrderDetailHistory.this, Login.class);
        startActivity(intent);
        finish();

        Toast.makeText(this, "Bạn đã đăng xuất!", Toast.LENGTH_SHORT).show();
    }
    void storedataInArray() {
        if(orderId == 0) return;
        Cursor cursor = myDB.getOrderDetailsByOrderId(orderId);
        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails = OrderDetail.ConvertCursorIntoListOrderDetail(cursor);
        if (orderDetails.isEmpty()) {
            Toast.makeText(this, "No data", Toast.LENGTH_LONG).show();
        } else {
            orderdetail_id.clear();
            orderdetail_product_id.clear();
            orderdetail_quantity.clear();
            orderdetail_total_money.clear();
            for (OrderDetail orderDetail: orderDetails) {
                orderdetail_id.add(String.valueOf(orderDetail.getId()));
                orderdetail_product_id.add(String.valueOf(orderDetail.getProductId()));
                orderdetail_quantity.add(String.valueOf(orderDetail.getQuantity()));
                orderdetail_total_money.add(String.valueOf(orderDetail.getTotalMoney()));
            }
        }
    }
}