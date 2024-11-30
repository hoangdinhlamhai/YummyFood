package com.example.yummyfood;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yummyfood.Domain.ChiTietDatBan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class book_detail_tb extends AppCompatActivity {

    TextView tvDate, tvStartTime, tvEndTime, tenBanTextView;
    EditText etGhiChu; // Thêm EditText cho ghi chú

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail_tb);

        tvDate = findViewById(R.id.tvDate);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvEndTime = findViewById(R.id.tvEndTime);
        tenBanTextView = findViewById(R.id.tenBanTextView);
        etGhiChu = findViewById(R.id.etGhiChu); // Khởi tạo EditText cho ghi chú

        // Nhận dữ liệu từ Intent (ví dụ: "Bàn 1")
        String tenBan = getIntent().getStringExtra("tenBan");
        tenBanTextView.setText(tenBan);

        // Hiển thị DatePicker khi nhấn vào "Ngày"
        tvDate.setOnClickListener(v -> showDatePicker());

        // Hiển thị TimePicker khi nhấn vào "Giờ bắt đầu"
        tvStartTime.setOnClickListener(v -> showStartTimePicker());

        // Hiển thị TimePicker khi nhấn vào "Giờ kết thúc"
        tvEndTime.setOnClickListener(v -> showEndTimePicker());

        // Nút xác nhận
        Button btnDatCoc = findViewById(R.id.datcoc);
        btnDatCoc.setOnClickListener(v -> {
            saveBooking(); // Gọi hàm lưu đặt bàn
        });

        // Nút quay lại
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> finish());
    }

    // Hiển thị DatePicker để chọn ngày
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                book_detail_tb.this,
                (view, year1, month1, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    tvDate.setText(selectedDate);
                },
                year, month, day);

        datePickerDialog.show();
    }

    // Hiển thị TimePicker để chọn giờ bắt đầu
    private void showStartTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                book_detail_tb.this,
                (view, hourOfDay, minute1) -> {
                    String selectedTime = hourOfDay + ":" + (minute1 < 10 ? "0" + minute1 : minute1);
                    tvStartTime.setText(selectedTime);
                },
                hour, minute, true);

        timePickerDialog.show();
    }

    // Hiển thị TimePicker để chọn giờ kết thúc
    private void showEndTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                book_detail_tb.this,
                (view, hourOfDay, minute1) -> {
                    String selectedTime = hourOfDay + ":" + (minute1 < 10 ? "0" + minute1 : minute1);
                    tvEndTime.setText(selectedTime);
                },
                hour, minute, true);

        timePickerDialog.show();
    }

    // Lưu thông tin đặt bàn vào Firebase
    private void saveBooking() {
        // Lấy dữ liệu từ các TextView và EditText
        String tenBan = tenBanTextView.getText().toString();
        String ngay = tvDate.getText().toString();
        String gioBatDau = tvStartTime.getText().toString();
        String gioKetThuc = tvEndTime.getText().toString();
        String ghiChu = etGhiChu.getText().toString();

        // Kiểm tra xem các trường có trống hay không
        if (tenBan.isEmpty() || ngay.isEmpty() || gioBatDau.isEmpty() || gioKetThuc.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra xem giờ đã có người đặt chưa
        checkIfTimeAvailable(ngay, gioBatDau, gioKetThuc, tenBan, ghiChu);
    }

    // Kiểm tra xem thời gian có trùng lặp với đơn đặt bàn khác không
    private void checkIfTimeAvailable(String ngay, String gioBatDau, String gioKetThuc, String tenBan, String ghiChu) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ChiTietDatBan");

        // Truy vấn tất cả các đơn đặt bàn trong cùng ngày
        Query query = myRef.orderByChild("Ngay").equalTo(ngay);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                boolean isAvailable = true;

                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    ChiTietDatBan existingBooking = snapshot.getValue(ChiTietDatBan.class);

                    if (existingBooking != null && existingBooking.getIdBan().equals(tenBan)) {
                        // Kiểm tra xung đột thời gian
                        if (isTimeConflict(existingBooking, gioBatDau, gioKetThuc)) {
                            isAvailable = false;
                            break;
                        }
                    }
                }

                if (isAvailable) {
                    // Nếu không có xung đột, lưu đơn đặt bàn mới vào Firebase
                    ChiTietDatBan chiTietDatBan = new ChiTietDatBan(ngay, ghiChu, tenBan, gioBatDau, gioKetThuc);
                    myRef.push().setValue(chiTietDatBan).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Toast.makeText(this, "Đặt bàn thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Đặt bàn thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Thông báo nếu giờ đã có người đặt
                    Toast.makeText(this, "Giờ đã có người đặt. Vui lòng chọn thời gian khác.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Lỗi khi truy vấn dữ liệu: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Kiểm tra xung đột thời gian
    private boolean isTimeConflict(ChiTietDatBan existingBooking, String newStartTime, String newEndTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        try {
            Date existingStart = sdf.parse(existingBooking.getThoiGianBatDau());
            Date existingEnd = sdf.parse(existingBooking.getThoiGianKetThuc());
            Date newStart = sdf.parse(newStartTime);
            Date newEnd = sdf.parse(newEndTime);

            // Kiểm tra nếu thời gian mới có sự xung đột với thời gian đã đặt trước
            return (newStart.before(existingEnd) && newEnd.after(existingStart));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
