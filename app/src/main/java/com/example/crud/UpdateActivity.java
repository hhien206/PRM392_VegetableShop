package com.example.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateActivity extends AppCompatActivity {
    public static String id_initial ,name_initial, category_initial, origincountry_initial;
    EditText name_input, category_input, origincountry_input;
    Button update_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);
        name_input = findViewById(R.id.name_input);
        name_input.setText(name_initial);
        category_input = findViewById(R.id.category_input);
        category_input.setText(category_initial);
        origincountry_input = findViewById(R.id.origincountry_input);
        origincountry_input.setText(origincountry_initial);
        update_button = findViewById(R.id.update_button);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.updateVegetable(id_initial,name_input.getText().toString().trim(),
                        category_input.getText().toString().trim(),
                        origincountry_input.getText().toString().trim()
                );
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public static void initialValues(String id, String name, String category, String origincountry){
        id_initial = id;
        name_initial = name;
        category_initial = category;
        origincountry_initial = origincountry;
    }
}
