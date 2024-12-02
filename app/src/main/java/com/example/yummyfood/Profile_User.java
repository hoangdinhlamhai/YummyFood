package com.example.yummyfood;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Profile_User extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        // Nút Lưu
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị AlertDialog
                new AlertDialog.Builder(Profile_User.this)
                        .setTitle("Thông tin đã được cập nhật")
                        .setMessage("Cảm ơn bạn đã cập nhật thông tin.")
                        .setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Chuyển sang MeUserActivity
                                Intent intent = new Intent(Profile_User.this, me_user.class);
                                startActivity(intent);
                                finish(); // Đóng Profile_User Activity
                            }
                        })
                        .show();
            }
        });

        // Nút Quay lại
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Đóng Activity hiện tại
            }
        });

        // Nút Đăng xuất
        Button logoutButton = findViewById(R.id.btnLogout);
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(Profile_User.this, Login_userActivity.class);
            startActivity(intent);
            finish(); // Đóng Activity hiện tại
        });

    }
}
