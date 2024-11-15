package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.yummyfood.admin.HomePage_Admin;

public class Login_userActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        // Edge-to-Edge cho layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.textView5), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Thiết lập sự kiện click cho TextView `textView5`
        TextView btn = findViewById(R.id.textView5);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_userActivity.this, activity_register_user.class);
                startActivity(intent);
            }
        });

        // Sự kiện click cho nút `btn_loginuser`
        Button btnLoginUser = findViewById(R.id.btn_loginuser);
        btnLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_userActivity.this, HomepageUserActivity.class);
                startActivity(intent);
            }
        });

        TextView viewMember = findViewById(R.id.viewMember);
        viewMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_userActivity.this, DanhSachThanhVien.class);
                startActivity(intent);
            }
        });
    }
}


//        RadioGroup radioGroup = findViewById(R.id.radioGroup);
//        Button btnLogin = findViewById(R.id.btn_loginuser);
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int selectedId = radioGroup.getCheckedRadioButtonId();
//                Intent intent;
//
//                if (selectedId == R.id.radioButtonUser) {
//                    intent = new Intent(Login_userActivity.this, HomepageUserActivity.class);
//                    startActivity(intent); // Bắt đầu Activity mới
//                } else if (selectedId == R.id.radioButtonAdmin) {
//                    intent = new Intent(Login_userActivity.this, HomePage_Admin.class);
//                    startActivity(intent);
//                }
//            }
//        });



