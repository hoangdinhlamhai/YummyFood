package com.example.yummyfood;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yummyfood.Domain.ChiTietDatBan;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class book_detail_tb extends AppCompatActivity {

    private TextView tenBanTextView, tvDate, tvStartTime, tvEndTime;
    private EditText etGhiChu;
    private Button btnDatBan, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail_tb);

        // Lấy tên bàn từ Intent
        String tenBan = getIntent().getStringExtra("tenBan");

        tenBanTextView = findViewById(R.id.tenBanTextView);
        tvDate = findViewById(R.id.tvDate);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvEndTime = findViewById(R.id.tvEndTime);
        etGhiChu = findViewById(R.id.etGhiChu);
        btnDatBan = findViewById(R.id.datcoc);
        btnBack = findViewById(R.id.btnBack);

        // Hiển thị tên bàn vào TextView
        tenBanTextView.setText(tenBan);

        // Set click listeners for DatePicker and TimePicker
        tvDate.setOnClickListener(v -> showDatePicker());
        tvStartTime.setOnClickListener(v -> showStartTimePicker());
        tvEndTime.setOnClickListener(v -> showEndTimePicker());

        btnDatBan.setOnClickListener(v -> saveBooking());
        btnBack.setOnClickListener(v -> finish());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            tvDate.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
        }, year, month, day);
        datePickerDialog.show();
    }

    private void showStartTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
            tvStartTime.setText(hourOfDay + ":" + minute1);
        }, hour, minute, true);
        timePickerDialog.show();
    }

    private void showEndTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
            tvEndTime.setText(hourOfDay + ":" + minute1);
        }, hour, minute, true);
        timePickerDialog.show();
    }

    private String getUserId() {
        SharedPreferences preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        return preferences.getString("userId", null);  // Lấy userId đã lưu
    }

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

        // Lưu thông tin vào Firebase
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("ChiTietDatBan");
        String bookingId = database.push().getKey();
        ChiTietDatBan booking = new ChiTietDatBan(tenBan, ngay, ghiChu, gioBatDau, gioKetThuc, userId);
        database.child(bookingId).setValue(booking)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(book_detail_tb.this, "Đặt bàn thành công!", Toast.LENGTH_SHORT).show();
                        finish(); // Quay lại màn hình trước
                    } else {
                        Toast.makeText(book_detail_tb.this, "Đặt bàn thất bại. Thử lại!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
