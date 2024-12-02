package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SearchUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        // Tìm ImageView bằng ID của icon_morong (imageView12)
        ImageView imageView = findViewById(R.id.button4);

        // Thiết lập sự kiện OnClickListener cho imageView
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng về HomepageUserActivity
                finish();
            }

        });
        LinearLayout item = findViewById(R.id.itemLayout);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchUserActivity.this, FoodDetailActivity.class);
                startActivity(intent);
            }
        });

        ImageButton addToCart = findViewById(R.id.btn_add_to_cart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchUserActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

    }
}
