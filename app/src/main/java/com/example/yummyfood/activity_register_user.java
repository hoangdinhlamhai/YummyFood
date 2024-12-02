package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yummyfood.Domain.KhachHang;
import com.example.yummyfood.Domain.TaiKhoan;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_register_user extends AppCompatActivity {
    EditText signupName, signupEMail, signupUsername, signupPassword;
    TextView loginRedirecText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        signupName = findViewById(R.id.txt_nameuser);
        signupEMail = findViewById(R.id.txt_sdt_user);
        signupUsername = findViewById(R.id.txt_pass_user);
        signupPassword = findViewById(R.id.txt_cf_pass_user);
        signupButton = findViewById(R.id.btn_create_user);
        loginRedirecText = findViewById(R.id.txt_w_create);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("TaiKhoan");

                String name = signupName.getText().toString();
                String email = signupEMail.getText().toString();
                String username = signupUsername.getText().toString();
                String pass = signupPassword.getText().toString();

                // Tạo ID tự động bằng cách sử dụng push()
                String userId = reference.push().getKey();

                // Lưu thông tin vào bảng TaiKhoan
                TaiKhoan taiKhoan = new TaiKhoan(pass, username);
                reference.child(userId).setValue(taiKhoan);

                // Lưu thông tin vào bảng KhachHang
                DatabaseReference khachHangRef = database.getReference("KhachHang");
                KhachHang khachHang = new KhachHang(email, name, userId);
                khachHangRef.child(userId).setValue(khachHang);

                Toast.makeText(activity_register_user.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity_register_user.this, Login_userActivity.class);
                startActivity(intent);
            }
        });

        loginRedirecText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_register_user.this, Login_userActivity.class);
                startActivity(intent);
            }
        });
    }
}