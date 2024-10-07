package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login_userActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user); // Layout được sử dụng là activity_login_user

        // Thiết lập Edge to Edge cho layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.textView5), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Tìm TextView có ID text_haveaccount
        TextView btn = findViewById(R.id.textView5);

        // Thiết lập sự kiện click cho TextView
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo Intent để chuyển sang activity_register_user
                Intent intent = new Intent(Login_userActivity.this, activity_register_user.class);
                startActivity(intent); // Bắt đầu Activity mới
            }
        });

        Button btnLogin = findViewById(R.id.btn_loginuser);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo Intent để chuyển từ Login_userActivity sang HomepageUserActivity
                Intent intent = new Intent(Login_userActivity.this, HomepageUserActivity.class);
                startActivity(intent); // Bắt đầu Activity mới
            }
        });

    }
}
