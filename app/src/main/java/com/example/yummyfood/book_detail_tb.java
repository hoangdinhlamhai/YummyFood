package com.example.yummyfood;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class book_detail_tb extends AppCompatActivity {

    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_detail_tb);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Nút xác nhận để hiển thị dialog
        Button btntb = findViewById(R.id.datcoc);
        btntb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(book_detail_tb.this, qr_coc_table.class);
                startActivity(intent);
            }
        });
        // thanh toan tien
        // Nút xác nhận để hiển thị dialog
        Button thanhtoan = findViewById(R.id.thanhtoan);
        thanhtoan.setOnClickListener(new View.OnClickListener() {
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

//        Button btnHuy = findViewById(R.id.cancel_button);
//        btnHuy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
        Button btnback = findViewById(R.id.btnBack);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
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


}