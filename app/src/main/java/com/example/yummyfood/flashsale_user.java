

package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class flashsale_user extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_flashsale_user);

            // Tìm ImageView bằng ID của icon_morong (imageView12)
            ImageView imageView = findViewById(R.id.button4);

            // Thiết lập sự kiện OnClickListener cho imageView
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển hướng về HomepageUserActivity
//                    Intent intent = new Intent(flashsale_user.this, HomepageUserActivity.class);
//                    startActivity(intent);
                    finish();
                }
            });

            ConstraintLayout product1 = findViewById(R.id.product1);
            product1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(flashsale_user.this, FoodDetailActivity.class);
                    startActivity(intent);
                }
            });

            ImageView addToCart = findViewById(R.id.product_add_to_cart1);
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(flashsale_user.this, CartActivity.class);
                    startActivity(intent);
                }
            });
            ConstraintLayout  chitiet = findViewById(R.id.product1);
            chitiet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(flashsale_user.this, FoodDetailActivity.class);
                    startActivity(intent);
                }
            });

            ConstraintLayout  chitiet2 = findViewById(R.id.product2);
            chitiet2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(flashsale_user.this, FoodDetailActivity.class);
                    startActivity(intent);
                }
            });

        }
    }
