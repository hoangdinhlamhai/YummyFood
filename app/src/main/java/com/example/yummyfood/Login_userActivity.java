package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Login_userActivity extends AppCompatActivity {
    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView signupRedirecText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        // Khởi tạo các view
        loginUsername = findViewById(R.id.editTextText);
        loginPassword = findViewById(R.id.editTextText2);
        loginButton = findViewById(R.id.btn_loginuser);
        signupRedirecText = findViewById(R.id.textView5);

        // Thiết lập listener cho nút đăng nhập
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateUsername() && validatePassword()) {
                    checkUser();
                }
            }
        });

        // Thiết lập listener cho nút chuyển đến đăng ký
        signupRedirecText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_userActivity.this, activity_register_user.class);
                startActivity(intent);
            }
        });
    }

    // Hàm kiểm tra tên người dùng có hợp lệ không
    public Boolean validateUsername() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()) {
            loginUsername.setError("Tên không được để trống");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }

    // Hàm kiểm tra mật khẩu có hợp lệ không
    public Boolean validatePassword() {
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Mật khẩu không được để trống");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }

    // Kiểm tra thông tin người dùng từ Firebase
    public void checkUser() {
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        // Truy cập Firebase Realtime Database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        // Thực hiện truy vấn để tìm người dùng theo username
        reference.child("users").child(userUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Lấy mật khẩu từ Firebase
                    String passwordFromDB = snapshot.child("password").getValue(String.class);

                    // So sánh mật khẩu người dùng nhập vào với mật khẩu lưu trong Firebase
                    if (passwordFromDB != null && passwordFromDB.equals(userPassword)) {
                        // Nếu đăng nhập thành công, chuyển sang trang chủ
                        Intent intent = new Intent(Login_userActivity.this, HomepageUserActivity.class);
                        startActivity(intent);
                    } else {
                        // Nếu mật khẩu không đúng, thông báo lỗi
                        loginPassword.setError("Mật khẩu không đúng");
                        loginPassword.requestFocus();
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
                Log.e("LoginError", "Error checking user: " + error.getMessage());
            }
        });
    }
}
