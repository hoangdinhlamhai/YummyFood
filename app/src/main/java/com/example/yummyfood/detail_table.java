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

public class detail_table extends AppCompatActivity {

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_table);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Nút xác nhận để hiển thị dialog
        Button btntb = findViewById(R.id.confirm_button);
        btntb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        // Nút quay lại
        Button btnb = findViewById(R.id.btnBack);
        btnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(detail_table.this, booktable_usser.class);
                startActivity(intent);
            }
        });

        // Chuyển qua chính sách đặt bàn
        TextView txt1 = findViewById(R.id.policy);
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(detail_table.this, policy_user.class);
                startActivity(intent);
            }
        });
    }

    // Hàm hiển thị dialog
    private void showDialog() {
        dialog = new Dialog(detail_table.this);
        dialog.setContentView(R.layout.activity_dialog_book_table);


        TextView exitButton = dialog.findViewById(R.id.dialog_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(detail_table.this, booktable_usser.class);
                startActivity(intent);
            }
        });

        dialog.show(); // Hiển thị dialog
    }
}
