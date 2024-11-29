package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FoodDetailActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail_user);
        Button btnReturnRetailFood = findViewById(R.id.btn_return_detailfood);

        // Thiết lập sự kiện nhấn nút
        btnReturnRetailFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc FoodRetailActivity để không trở lại được bằng nút quay lại
            }
        });

        // Ánh xạ các View
        ImageView ivFoodImage = findViewById(R.id.ivFoodImage);
        TextView tvFoodName = findViewById(R.id.tvFoodName);
        TextView tvFoodPrice = findViewById(R.id.tvFoodPrice);
        TextView tvFoodDescription = findViewById(R.id.tvFoodDescription);

        // Nhận ID món ăn từ Intent
        String foodId = getIntent().getStringExtra("idMonAn");

        // Kết nối Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("MonAn");

        // Truy vấn chi tiết món ăn
        if (foodId != null) {
            databaseReference.child(foodId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String tenMonAn = snapshot.child("tenMonAn").getValue(String.class);
                        int donGia = snapshot.child("donGia").getValue(Integer.class);
                        String moTa = snapshot.child("moTa").getValue(String.class);
                        String hinhAnh = snapshot.child("hinhAnh").getValue(String.class);

                        // Đổ dữ liệu lên giao diện
                        tvFoodName.setText(tenMonAn);
                        tvFoodPrice.setText(donGia + "đ");
                        tvFoodDescription.setText(moTa);
                        Glide.with(FoodDetailActivity.this).load(hinhAnh).into(ivFoodImage);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Xử lý lỗi nếu cần
                }
            });
        }


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