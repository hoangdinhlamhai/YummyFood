package com.example.yummyfood.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.yummyfood.R;

public class Edit_Food extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_food);

        // Khởi tạo AutoCompleteTextView
        autoCompleteCategory = findViewById(R.id.autoCompleteCategory);
        String[] categories = {"Food", "Drinks", "Desserts", "Snacks", "Appetizers"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.item_category, categories);

        // Gán adapter cho AutoCompleteTextView
        autoCompleteCategory.setAdapter(adapter);

        // Thêm sự kiện chọn mục
        autoCompleteCategory.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCategory = adapter.getItem(position);
            // Xử lý sự kiện chọn mục
            // Ví dụ: Hiển thị một Toast
            Toast.makeText(this, "Selected: " + selectedCategory, Toast.LENGTH_SHORT).show();
        });

        //back to homepage
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}