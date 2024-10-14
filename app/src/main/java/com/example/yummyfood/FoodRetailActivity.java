package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FoodRetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_retail_user);
        // Xử lý giao diện cho FoodRetailActivity ở đây
        // Khởi tạo nút btn_return_retailfood
        Button btnReturnRetailFood = findViewById(R.id.btn_return_retailfood);

        // Thiết lập sự kiện nhấn nút
        btnReturnRetailFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển về HomepageUserActivity
                Intent intent = new Intent(FoodRetailActivity.this, HomepageUserActivity.class);
                startActivity(intent);
                finish(); // Kết thúc FoodRetailActivity để không trở lại được bằng nút quay lại
            }
        });
    }
}
