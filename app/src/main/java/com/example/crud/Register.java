package com.example.crud;

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

public class Register extends AppCompatActivity {

    EditText usernameInput, passwordInput, confirmPasswordInput;
    Button registerButton;
    MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        confirmPasswordInput = findViewById(R.id.confirm_password);
        registerButton = findViewById(R.id.register_button);

        myDB = new MyDatabaseHelper(this);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                String confirmPassword = confirmPasswordInput.getText().toString().trim();

                if (password.equals(confirmPassword)) {
                    if (checkUserExists(username)) {
                        Toast.makeText(Register.this, "Tên đăng nhập đã tồn tại!", Toast.LENGTH_SHORT).show();
                    } else {
                        myDB.addUser(username, password, null);
                        Toast.makeText(Register.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        finish(); // Quay về màn hình trước đó sau khi đăng ký thành công
                    }
                } else {
                    Toast.makeText(Register.this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean checkUserExists(String username) {
        Cursor cursor = myDB.readAllUsers();

        // Duyệt qua dữ liệu để kiểm tra xem có người dùng nào có tên đăng nhập giống với 'username' hay không
        while (cursor.moveToNext()) {
            String existingUsername = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            if (existingUsername.equals(username)) {
                return true; // Người dùng đã tồn tại
            }
        }

        cursor.close();
        return false; // Người dùng không tồn tại
    }
}