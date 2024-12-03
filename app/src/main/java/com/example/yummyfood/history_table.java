package com.example.yummyfood;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yummyfood.Domain.ChiTietDatBan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class history_table extends AppCompatActivity {

    // Khai báo các View
    TextView tvDate, tvStartTime, tvEndTime, tvNote, tvStatusBooking, tvTableName;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_table);

        // Khởi tạo các view
        tvDate = findViewById(R.id.tvDate);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvEndTime = findViewById(R.id.tvEndTime);
        tvNote = findViewById(R.id.tvNote);
        tvStatusBooking = findViewById(R.id.status_booking);
        tvTableName = findViewById(R.id.tenban); // Khởi tạo TextView cho tên bàn
        btnCancel = findViewById(R.id.cancel_button);

        // Xử lý sự kiện quay lại
        ImageView btnBack = findViewById(R.id.back_button);
        btnBack.setOnClickListener(v -> finish());

        // Lấy thông tin đặt bàn từ Firebase cho tài khoản người dùng
        getBookingDetails();

        // Xử lý sự kiện hủy đặt bàn
        btnCancel.setOnClickListener(v -> cancelBooking());
    }

    // Lấy chi tiết đặt bàn từ Firebase
    private void getBookingDetails() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference bookingRef = database.getReference("ChiTietDatBan");

        // Lấy userId từ SharedPreferences
        String userId = getUserId();

        // Truy vấn Firebase với userId đã lưu
        bookingRef.orderByChild("userId").equalTo(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                Log.d("HistoryTable", "Kết quả truy vấn: " + dataSnapshot.toString());

                if (dataSnapshot.exists()) {
                    boolean found = false; // Biến đánh dấu tìm thấy thông tin
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ChiTietDatBan bookingDetails = snapshot.getValue(ChiTietDatBan.class);
                        if (bookingDetails != null) {
                            // Hiển thị thông tin đặt bàn
                            tvStatusBooking.setText("Đã đặt");
                            tvDate.setText("Ngày: " + bookingDetails.getNgay());
                            tvStartTime.setText("Giờ bắt đầu: " + bookingDetails.getThoiGianBatDau());
                            tvEndTime.setText("Giờ kết thúc: " + bookingDetails.getThoiGianKetThuc());
                            tvNote.setText("Dặn dò: " + bookingDetails.getGhiChu());
                            tvTableName.setText("Tên bàn: " + bookingDetails.getTenBan()); // Hiển thị tên bàn
                            found = true;
                            break; // Dừng lại sau khi tìm thấy
                        }
                    }
                    if (!found) {
                        Toast.makeText(this, "Không tìm thấy thông tin đặt bàn của bạn", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Không tìm thấy thông tin đặt bàn", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Lỗi khi truy vấn dữ liệu: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Hủy đặt bàn trong Firebase
    private void cancelBooking() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference bookingRef = database.getReference("ChiTietDatBan");

        // Lấy userId từ SharedPreferences
        String userId = getUserId();

        // Truy vấn để lấy đặt bàn của người dùng
        bookingRef.orderByChild("userId").equalTo(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ChiTietDatBan bookingDetails = snapshot.getValue(ChiTietDatBan.class);
                        if (bookingDetails != null) {
                            String bookingId = snapshot.getKey();
                            if (bookingId != null) {
                                // Xóa đặt bàn từ Firebase
                                bookingRef.child(bookingId).removeValue().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(this, "Đã hủy đặt bàn", Toast.LENGTH_SHORT).show();
                                        finish();  // Quay lại màn hình trước
                                    } else {
                                        Toast.makeText(this, "Hủy đặt bàn thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            return; // Dừng lại sau khi tìm thấy
                        }
                    }
                    Toast.makeText(this, "Không tìm thấy thông tin đặt bàn để hủy", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Lỗi khi truy vấn dữ liệu: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Lấy userId từ SharedPreferences
    private String getUserId() {
        SharedPreferences preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        return preferences.getString("userId", null);  // Lấy userId đã lưu
    }
}