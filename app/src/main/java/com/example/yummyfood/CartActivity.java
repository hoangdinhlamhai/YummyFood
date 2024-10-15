package com.example.yummyfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        // Xử lý giao diện cho FoodRetailActivity ở đây


        Button btnReturnCart = findViewById(R.id.btn_return_cart);

        // Thiết lập sự kiện nhấn nút
        btnReturnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển về HomepageUserActivity
                Intent intent = new Intent(CartActivity.this, HomepageUserActivity.class);
                startActivity(intent);
                finish(); // Kết thúc FoodRetailActivity để không trở lại được bằng nút quay lại
            }
        });
    }
}
