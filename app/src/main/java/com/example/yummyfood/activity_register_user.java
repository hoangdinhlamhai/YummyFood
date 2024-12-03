package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yummyfood.Domain.KhachHang;
import com.example.yummyfood.Domain.TaiKhoan;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_register_user extends AppCompatActivity {
    EditText signupName, signupEMail, signupUsername, signupPassword;
    TextView loginRedirectText;
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
        loginRedirectText = findViewById(R.id.txt_w_create);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = signupName.getText().toString().trim();
                String email = signupEMail.getText().toString().trim();
                String username = signupUsername.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();

                // Kiểm tra thông tin đầu vào
                if (name.isEmpty() || email.isEmpty() || username.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(activity_register_user.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("TaiKhoan");
                String userId = reference.push().getKey(); // Tạo ID tự động

                TaiKhoan taiKhoan = new TaiKhoan(pass, username);
                reference.child(userId).setValue(taiKhoan).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Lưu thông tin vào bảng KhachHang
                        DatabaseReference khachHangRef = database.getReference("KhachHang");
                        KhachHang khachHang = new KhachHang(email, name, userId);
                        khachHangRef.child(userId).setValue(khachHang).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(activity_register_user.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(activity_register_user.this, Login_userActivity.class);
                                startActivity(intent);
                                finish(); // Đóng Activity đăng ký
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity_register_user.this, "Lỗi khi lưu thông tin khách hàng!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity_register_user.this, "Lỗi khi lưu thông tin tài khoản!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_register_user.this, Login_userActivity.class);
                startActivity(intent);
                finish(); // Đóng Activity đăng ký
            }
        });
    }
}