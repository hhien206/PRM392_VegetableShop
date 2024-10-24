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
                if(username.equals("admin@gmail.com") && password.equals("12345")){
                    Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    // Chuyển sang Activity khác nếu đăng nhập thành công
                    account_role = "Admin";
                    account_Id = 0;
                    Cursor cursor1 = myDB.getOrderByUserId(Login.account_Id);
                    if (cursor1.moveToFirst()) {
                        int orderIdColumnIndex = cursor1.getColumnIndex("order_id");
                        if (orderIdColumnIndex != -1) {
                            int orderId = cursor1.getInt(orderIdColumnIndex);
                            CardActivity.orderId = orderId;
                        }
                    }
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }
                else if ((cursor = myDB.checkUser(username, password))!=null) {
                    Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    // Chuyển sang Activity khác nếu đăng nhập thành công
                    account_role = "User";
                    account_Id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("COLUMN_USER_ID")));
                    Cursor cursor1 = myDB.getOrderByUserId(Login.account_Id);
                    if (cursor1.moveToFirst()) {
                        int orderIdColumnIndex = cursor1.getColumnIndex("order_id");
                        if (orderIdColumnIndex != -1) {
                            int orderId = cursor1.getInt(orderIdColumnIndex);
                            CardActivity.orderId = orderId;
                        }
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
    }
}