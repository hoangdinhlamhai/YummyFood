package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Button btnReturnCart = findViewById(R.id.btn_return_cart);


        btnReturnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển về HomepageUserActivity
                Intent intent = new Intent(CartActivity.this, HomepageUserActivity.class);
                startActivity(intent);
                finish();
            }
        });


        setupUIInteractions();
    }


    private void setupUIInteractions() {
        LinearLayout mon1 = findViewById(R.id.mon1);
        LinearLayout mon2 = findViewById(R.id.mon2);
        LinearLayout mon3 = findViewById(R.id.mon3);


        mon1.setOnClickListener(v -> startActivity(new Intent(CartActivity.this, FoodRetailActivity.class)));
        mon2.setOnClickListener(v -> startActivity(new Intent(CartActivity.this, FoodRetailActivity.class)));
        mon3.setOnClickListener(v -> startActivity(new Intent(CartActivity.this, FoodRetailActivity.class)));
    }
}
