package com.example.yummyfood;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Adapter.ReviewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rate_Order_User extends AppCompatActivity {
    private RecyclerView reviewRecyclerView;
    private ReviewAdapter reviewAdapter;
    private List<Map<String, Object>> reviewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_order_user);

        // Ánh xạ RecyclerView
        reviewRecyclerView = findViewById(R.id.reviewRecyclerView);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo danh sách và Adapter
        reviewsList = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(this, reviewsList);
        reviewRecyclerView.setAdapter(reviewAdapter);

        // Tải dữ liệu từ Firebase
        loadReviews();

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(view -> finish());
    }

    private void loadReviews() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("DanhGia");

        ref.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reviewsList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Map<String, Object> review = (Map<String, Object>) data.getValue();
                    reviewsList.add(review);
                }
                reviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Rate_Order_User.this, "Lỗi tải đánh giá: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Firebase", "Error: ", error.toException());
            }
        });
    }
}
