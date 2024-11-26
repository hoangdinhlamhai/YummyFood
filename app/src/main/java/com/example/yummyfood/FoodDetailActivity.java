package com.example.yummyfood;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FoodDetailActivity extends AppCompatActivity {

    TextView tvFoodName, tvFoodPrice, tvFoodDescription;
    ImageView ivFoodImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail_user);

        Button btn_back = findViewById(R.id.btn_back);

        // Thiết lập sự kiện nhấn nút
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Ánh xạ view
        tvFoodName = findViewById(R.id.foodDetailName);
        tvFoodPrice = findViewById(R.id.foodDetailPrice);
        ivFoodImage = findViewById(R.id.foodDetailImg);
        tvFoodDescription = findViewById(R.id.foodDetailDescription);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String foodName = intent.getStringExtra("food_name");
        int foodPrice = intent.getIntExtra("food_price", 0);
        String foodDesc = intent.getStringExtra("food_description");
        byte[] foodImage = intent.getByteArrayExtra("food_image");

        // Hiển thị dữ liệu
        tvFoodName.setText(foodName);
        tvFoodPrice.setText(foodPrice + "đ");
        tvFoodDescription.setText(foodDesc);

        // Convert byte[] to Bitmap and display image
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        ivFoodImage.setImageBitmap(bitmap);


        // nhấn vào nút thanh toán thì chuyển đến trang thanh toán
        Button btnPayingRetailFood = findViewById(R.id.btn_paying_retailfood);
        btnPayingRetailFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến ActivityPaymentUser
                Intent intent = new Intent(FoodDetailActivity.this, ActivityPaymentUser.class);
                startActivity(intent);
            }
        });
    }
}
