package com.example.yummyfood;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class book_detail_tb extends AppCompatActivity {

    TextView tvDate, tvTime;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail_tb);  // Gọi setContentView một lần duy nhất

        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);

        // Hiển thị DatePicker khi nhấn vào "Ngày"
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // Hiển thị TimePicker khi nhấn vào "Giờ"
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        // Nút xác nhận để hiển thị dialog
        Button btntb = findViewById(R.id.datcoc);
        btntb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(book_detail_tb.this, qr_tinhtien_ban.class);
                startActivity(intent);
            }
        });

        // Chuyển qua chính sách đặt bàn
        TextView txt1 = findViewById(R.id.policy);
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(book_detail_tb.this, user_policy.class);
                startActivity(intent);
            }
        });

        // Nút quay lại
        Button btnback = findViewById(R.id.btnBack);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // Hàm hiển thị dialog
    private void showDialog() {
        dialog = new Dialog(book_detail_tb.this);
        dialog.setContentView(R.layout.activity_dialog_book_table);

        TextView exitButton = dialog.findViewById(R.id.dialog_xem);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(book_detail_tb.this, history_table.class);
                startActivity(intent);
            }
        });

        dialog.show(); // Hiển thị dialog
    }

    // Hàm chọn ngày (chỉ cho phép chọn ngày trong tương lai)
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

        // Đặt ngày hiện tại làm ngày tối thiểu
        calendar.set(year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());  // Chỉ cho phép chọn ngày trong tương lai

        datePickerDialog.show();
    }

    // Hàm chọn giờ (không cho chọn khung giờ từ 7-9 sáng)
    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                book_detail_tb.this,
                (view, hourOfDay, minute1) -> {
                    // Kiểm tra nếu giờ nằm trong khoảng 7-9 giờ sáng thì không cho chọn
                    if (hourOfDay >= 7 && hourOfDay <= 9) {
                        Toast.makeText(book_detail_tb.this, "Khung giờ từ 7-9 sáng đã có người đặt!", Toast.LENGTH_SHORT).show();
                    } else {
                        String selectedTime = hourOfDay + ":" + (minute1 < 10 ? "0" + minute1 : minute1);
                        tvTime.setText(selectedTime);
                    }
                },
                hour, minute, true);

        timePickerDialog.show();
    }
}
