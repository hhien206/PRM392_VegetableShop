package com.example.crud;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.crud.model.Order;
import com.example.crud.model.User;

public class Login extends AppCompatActivity {

    public static String account_role;
    public static int account_Id;
    EditText editTextUsername, editTextPassword;
    Button buttonLogin;
    MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        Button buttonMap = findViewById(R.id.map_button);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        myDB = new MyDatabaseHelper(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                Cursor cursor;
                User user = new User();
                if(username.equals("admin@gmail.com") && password.equals("12345")){
                    Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    // Chuyển sang Activity khác nếu đăng nhập thành công
                    account_role = "Admin";
                    account_Id = 0;
                    Cursor cursor1 = myDB.getCartByUserId(Login.account_Id);
                    Order order = new Order();
                    order = Order.ConvertCursorIntoOrder(cursor1);
                    if(order != null){
                        CardActivity.orderId = order.getId();
                    }
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }
                else if ((user = (User.ConvertCursorIntoUser(cursor = myDB.checkUser(username, password)))) != null) {
                    Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    // Chuyển sang Activity khác nếu đăng nhập thành công
                    account_role = "User";
                    if (cursor.moveToFirst()) {
                        account_Id = user.getId();
                    }
                    Cursor cursor1 = myDB.getCartByUserId(Login.account_Id);
                    Order order = new Order();
                    order = Order.ConvertCursorIntoOrder(cursor1);
                    if(order != null){
                        CardActivity.orderId = order.getId();
                    }
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Login.this, "Đăng nhập thất bại! Kiểm tra lại thông tin.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }
}