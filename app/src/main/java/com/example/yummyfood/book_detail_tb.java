package com.example.yummyfood;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Adapter.BookingTimeAdapter;
import com.example.yummyfood.Domain.ChiTietDatBan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class book_detail_tb extends AppCompatActivity {

    private TextView tenBanTextView, tvDate, tvStartTime, tvEndTime;
    private EditText etGhiChu;
    private Button btnDatBan, btnBack;
    private RecyclerView horizontalRecyclerView;
    private BookingTimeAdapter bookingTimeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail_tb);

        // Lấy tên bàn từ Intent
        String tenBan = getIntent().getStringExtra("tenBan");
        if (tenBan == null) {
            Toast.makeText(this, "Tên bàn không hợp lệ", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Khởi tạo các view
        tenBanTextView = findViewById(R.id.tenBanTextView);
        tvDate = findViewById(R.id.tvDate);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvEndTime = findViewById(R.id.tvEndTime);
        etGhiChu = findViewById(R.id.etGhiChu);
        btnDatBan = findViewById(R.id.datcoc);
        btnBack = findViewById(R.id.btnBack);
        horizontalRecyclerView = findViewById(R.id.horizontalRecyclerView);

        // Hiển thị tên bàn vào TextView
        tenBanTextView.setText(tenBan);

        // Cài đặt sự kiện click cho các trường ngày, giờ
        tvDate.setOnClickListener(v -> showDatePicker());
        tvStartTime.setOnClickListener(v -> showStartTimePicker());
        tvEndTime.setOnClickListener(v -> showEndTimePicker());

        btnDatBan.setOnClickListener(v -> saveBooking());
        btnBack.setOnClickListener(v -> finish());

        // Gọi phương thức để tải giờ đặt bàn
        loadBookingTimes(tenBan);
    }

    // Hàm hiển thị dialog chọn ngày
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Thiết lập ngày tối thiểu là ngày hiện tại
        Calendar minDate = Calendar.getInstance();
        minDate.set(year, month, day);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> tvDate.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1),
                year, month, day);

        datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis()); // Thiết lập ngày tối thiểu
        datePickerDialog.show();
    }

    // Hàm hiển thị dialog chọn giờ bắt đầu
    private void showStartTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        // TimePickerDialog chỉ cho phép chọn giờ
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> tvStartTime.setText(String.format("%02d:00", hourOfDay)), // Chỉ hiển thị giờ
                hour, 0, true); // Đặt phút mặc định là 0
        timePickerDialog.show();
    }

    private void showEndTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        // TimePickerDialog chỉ cho phép chọn giờ
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> tvEndTime.setText(String.format("%02d:00", hourOfDay)), // Chỉ hiển thị giờ
                hour, 0, true); // Đặt phút mặc định là 0
        timePickerDialog.show();
    }

    // Lấy ID người dùng từ SharedPreferences
    private String getUserId() {
        SharedPreferences preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        return preferences.getString("userId", null);  // Lấy userId đã lưu
    }

    // Hàm lưu thông tin đặt bàn vào Firebase
    private void saveBooking() {
        String tenBan = tenBanTextView.getText().toString();
        String ngay = tvDate.getText().toString();
        String gioBatDau = tvStartTime.getText().toString();
        String gioKetThuc = tvEndTime.getText().toString();
        String ghiChu = etGhiChu.getText().toString();
        String userId = getUserId();  // Lấy userId từ SharedPreferences

        // Kiểm tra userId không null
        if (userId == null) {
            Toast.makeText(this, "Vui lòng đăng nhập trước khi đặt bàn", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(book_detail_tb.this, Login_userActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Kiểm tra xem các trường có trống không
        if (tenBan.isEmpty() || ngay.isEmpty() || gioBatDau.isEmpty() || gioKetThuc.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra thời gian bắt đầu và kết thúc
        if (gioBatDau.compareTo(gioKetThuc) >= 0) {
            Toast.makeText(this, "Giờ kết thúc phải sau giờ bắt đầu", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra trùng lặp đặt bàn
        checkBookingConflict(tenBan, ngay, gioBatDau, gioKetThuc, ghiChu, userId);
    }

    private void checkBookingConflict(String tenBan, String ngay, String gioBatDau, String gioKetThuc, String ghiChu, String userId) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("ChiTietDatBan");
        database.orderByChild("tenBan").equalTo(tenBan).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    ChiTietDatBan existingBooking = snapshot.getValue(ChiTietDatBan.class);
                    if (existingBooking != null && existingBooking.getNgay().equals(ngay)) {
                        // Kiểm tra xung đột thời gian
                        if (isTimeConflict(gioBatDau, gioKetThuc, existingBooking.getThoiGianBatDau(), existingBooking.getThoiGianKetThuc())) {
                            Toast.makeText(this, "Giờ này đã có người đặt, vui lòng chọn giờ khác!", Toast.LENGTH_SHORT).show();
                            return; // Kết thúc hàm nếu có xung đột
                        }
                    }
                }
                // Nếu không có xung đột, lưu đặt bàn
                saveBookingToFirebase(tenBan, ngay, ghiChu, gioBatDau, gioKetThuc, userId);
            } else {
                Toast.makeText(this, "Lỗi khi kiểm tra đặt bàn: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isTimeConflict(String gioBatDau1, String gioKetThuc1, String gioBatDau2, String gioKetThuc2) {
        return (gioBatDau1.compareTo(gioKetThuc2) < 0) && (gioBatDau2.compareTo(gioKetThuc1) < 0);
    }

    private void saveBookingToFirebase(String tenBan, String ngay, String ghiChu, String gioBatDau, String gioKetThuc, String userId) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("ChiTietDatBan");
        String bookingId = database.push().getKey();

        // Đặt giá trị mặc định cho trangThai
        String trangThai = "Đã đặt"; // hoặc bất kỳ trạng thái nào phù hợp

        // Tạo một đối tượng ChiTietDatBan mới
        ChiTietDatBan booking = new ChiTietDatBan(tenBan, ngay, ghiChu, gioBatDau, gioKetThuc, userId, trangThai);

        database.child(bookingId).setValue(booking).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(book_detail_tb.this, "Đặt bàn thành công!", Toast.LENGTH_SHORT).show();
                finish(); // Quay lại màn hình trước
            } else {
                Toast.makeText(book_detail_tb.this, "Đặt bàn thất bại. Thử lại!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Hàm tải danh sách giờ đặt bàn
    private void loadBookingTimes(String tenBan) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("ChiTietDatBan");
        database.orderByChild("tenBan").equalTo(tenBan).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<ChiTietDatBan> bookingList = new ArrayList<>();
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    ChiTietDatBan booking = snapshot.getValue(ChiTietDatBan.class);
                    if (booking != null) {
                        bookingList.add(booking);
                    }
                }
                // Cập nhật RecyclerView
                bookingTimeAdapter = new BookingTimeAdapter(bookingList);
                horizontalRecyclerView.setAdapter(bookingTimeAdapter);
                horizontalRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            } else {
                Toast.makeText(this, "Lỗi khi tải dữ liệu: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}