package com.example.crud;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud.model.CreateOrder;
import com.example.crud.model.OrderDetail;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class CardActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    ArrayList<String> orderdetail_id, orderdetail_product_id, orderdetail_quantity, orderdetail_total_money;
    OrderDetailAdapter orderDetailAdapter;
    public static int orderId = 0;
    FloatingActionButton buy_button;
    static String status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card);
        myDB = new MyDatabaseHelper(CardActivity.this);

        if(status.equals("Success")){
            status = "";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = formatter.format(new Date());
            Cursor cursor = myDB.getOrderDetailsByOrderId(orderId);
            List<OrderDetail> orderDetails = new ArrayList<>();
            orderDetails = OrderDetail.ConvertCursorIntoListOrderDetail(cursor);
            int quantity = getAllQuantityOrder(orderDetails);
            double price = getAllPriceOrder(orderDetails);
            myDB.updateOrderStatusAndDate(orderId,"COMPLETED", formattedDate, quantity, price);
            orderId = 0;
            Intent intent = new Intent(CardActivity.this, MainActivity.class);
            startActivity(intent);
        }

        recyclerView = findViewById(R.id.recycleView);
        buy_button = findViewById(R.id.buy_button);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ZaloPaySDK.init(2553, Environment.SANDBOX);

        buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrder orderApi = new CreateOrder();
                String token = "";
                try {
                    JSONObject data = orderApi.createOrder("15000");
                    String code = data.getString("return_code");
                    if (code.equals("1")) {
                        token = data.getString("zp_trans_token");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                status = "Success";
                ZaloPaySDK.getInstance().payOrder(CardActivity.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialog.Builder(CardActivity.this)
                                        .setTitle("Payment Success")
                                        .setMessage(String.format("TransactionId: %s - TransToken: %s", transactionId, transToken))
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .setNegativeButton("Cancel", null).show();
                            }
                        });
                    }

                    @Override
                    public void onPaymentCanceled(String zpTransToken, String appTransID) {
                        new AlertDialog.Builder(CardActivity.this)
                                .setTitle("User Cancel Payment")
                                .setMessage(String.format("zpTransToken: %s \n", zpTransToken))
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setNegativeButton("Cancel", null).show();
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                        new AlertDialog.Builder(CardActivity.this)
                                .setTitle("Payment Fail")
                                .setMessage(String.format("ZaloPayErrorCode: %s \nTransToken: %s", zaloPayError.toString(), zpTransToken))
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setNegativeButton("Cancel", null).show();
                    }

                });
            }
        });





        /*buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = formatter.format(new Date());
                Cursor cursor = myDB.getOrderDetailsByOrderId(orderId);
                List<OrderDetail> orderDetails = new ArrayList<>();
                orderDetails = OrderDetail.ConvertCursorIntoListOrderDetail(cursor);
                int quantity = getAllQuantityOrder(orderDetails);
                double price = getAllPriceOrder(orderDetails);
                myDB.updateOrderStatusAndDate(orderId,"COMPLETED", formattedDate, quantity, price);
                orderId = 0;
                Intent intent = new Intent(CardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });*/

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        myDB = new MyDatabaseHelper(CardActivity.this);
        orderdetail_id = new ArrayList<>();
        orderdetail_product_id = new ArrayList<>();
        orderdetail_quantity = new ArrayList<>();
        orderdetail_total_money = new ArrayList<>();

        storedataInArray();
        orderDetailAdapter = new OrderDetailAdapter(CardActivity.this, orderdetail_id,orderdetail_product_id,orderdetail_quantity,orderdetail_total_money);
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
    int getAllQuantityOrder(List<OrderDetail> orderDetails){
        int totalQuantity = 0;

        if (orderDetails != null) {
            for (OrderDetail orderDetail: orderDetails) {
                int quantity = orderDetail.getQuantity();
                totalQuantity += quantity;
            }
        }

        return totalQuantity;
    }
    double getAllPriceOrder(List<OrderDetail> orderDetails){
        double totalPrice = 0.0;

        if (orderDetails != null) {
            for (OrderDetail orderDetail: orderDetails) {
                double price = orderDetail.getTotalMoney();
                totalPrice += price;
            }
        }

        return totalPrice;
    }
}
