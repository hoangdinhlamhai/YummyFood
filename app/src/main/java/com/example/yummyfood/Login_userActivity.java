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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_userActivity extends AppCompatActivity {

    private EditText loginUsername, loginPassword;
    private Button loginButton;
    private TextView signupRedirecText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        loginUsername = findViewById(R.id.editTextText);
        loginPassword = findViewById(R.id.editTextText2);
        loginButton = findViewById(R.id.btn_loginuser);
        signupRedirecText = findViewById(R.id.textView5);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser();
            }
        });

        // Thêm OnClickListener cho TextView để chuyển đến giao diện đăng ký
        signupRedirecText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_userActivity.this, activity_register_user.class);
                startActivity(intent);
            }
        });
    }

    // Kiểm tra người dùng đăng nhập
    public void checkUser() {
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        // Truy cập Firebase Realtime Database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        // Thực hiện truy vấn để tìm người dùng theo tên tài khoản
        reference.child("TaiKhoan").orderByChild("tenTaiKhoan").equalTo(userUsername)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Lấy thông tin người dùng
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                String passwordFromDB = userSnapshot.child("matKhau").getValue(String.class);

                                // So sánh mật khẩu người dùng nhập vào với mật khẩu lưu trong Firebase
                                if (passwordFromDB != null && passwordFromDB.equals(userPassword)) {
                                    // Chuyển đến trang chủ
                                    Intent intent = new Intent(Login_userActivity.this, HomepageUserActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Nếu mật khẩu không đúng, thông báo lỗi
                                    loginPassword.setError("Mật khẩu không đúng");
                                    loginPassword.requestFocus();
                                }
                            }
                        } else {
                            // Nếu tên người dùng không tồn tại, thông báo lỗi
                            loginUsername.setError("Tên người dùng không tồn tại");
                            loginUsername.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Xử lý khi có lỗi khi truy vấn Firebase
                        Toast.makeText(Login_userActivity.this, "Lỗi đăng nhập, thử lại", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}