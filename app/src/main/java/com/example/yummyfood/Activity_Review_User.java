package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Activity_Review_User extends AppCompatActivity {
    private EditText edtFoodReview;
    private RatingBar ratingBar;
    private String orderId, accountId; // ID đơn hàng và tài khoản
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_user);

        // Ánh xạ giao diện
        edtFoodReview = findViewById(R.id.food_description_edit_text);
        ratingBar = findViewById(R.id.ratingBar);
        Button btnSaveReview = findViewById(R.id.btn_save_review);

        // Lấy dữ liệu từ Intent
        orderId = getIntent().getStringExtra("orderId");
        accountId = getIntent().getStringExtra("accountId");

        databaseReference = FirebaseDatabase.getInstance().getReference("DanhGia");

        // Nút lưu đánh giá
        btnSaveReview.setOnClickListener(v -> saveReview());
    }

    private void saveReview() {
        String reviewContent = edtFoodReview.getText().toString().trim();
        float ratingStars = ratingBar.getRating();

        if (reviewContent.isEmpty() || ratingStars == 0) {
            Toast.makeText(this, "Vui lòng nhập nội dung và số sao đánh giá!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy ngày giờ hiện tại
        String currentDateTime = getCurrentDateTime();

        // Chuẩn bị dữ liệu đánh giá
        Map<String, Object> reviewData = new HashMap<>();
        reviewData.put("idDonHang", orderId);
        reviewData.put("idTaiKhoan", accountId);
        reviewData.put("danhGia", reviewContent);
        reviewData.put("soSao", ratingStars);
        reviewData.put("ngayDanhGia", currentDateTime); // Thêm ngày giờ đánh giá

        DatabaseReference reviewRef = FirebaseDatabase.getInstance().getReference("DanhGia");
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("ChiTietDonHang").child(orderId);

        // Lưu đánh giá
        reviewRef.push().setValue(reviewData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Cập nhật trạng thái đã đánh giá
                orderRef.child("isReviewed").setValue(true).addOnCompleteListener(updateTask -> {
                    if (updateTask.isSuccessful()) {
                        Toast.makeText(this, "Đánh giá thành công!", Toast.LENGTH_SHORT).show();
                        finish(); // Quay lại màn hình trước
                    } else {
                        Toast.makeText(this, "Lỗi cập nhật trạng thái!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Lỗi khi gửi đánh giá!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Phương thức lấy ngày giờ hiện tại
    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }


    }
