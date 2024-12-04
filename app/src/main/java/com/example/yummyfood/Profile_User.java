package com.example.yummyfood;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Profile_User extends AppCompatActivity {

    // Khai báo các view
    EditText emailEditText, usernameEditText, phoneEditText, addressEditText, passwordEditText, confirmPasswordEditText;
    Button saveButton, btnBack, btnLogout;
    DatabaseReference accountRef, customerRef, addressRef;
    String userId; // ID người dùng (lấy từ đăng nhập)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        // Lấy các đối tượng từ layout
        emailEditText = findViewById(R.id.email_edit_text);
        usernameEditText = findViewById(R.id.username_edit_text);
        phoneEditText = findViewById(R.id.phone_edit_text);
        addressEditText = findViewById(R.id.address_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        saveButton = findViewById(R.id.save_button);
        btnBack = findViewById(R.id.btn_back);
        btnLogout = findViewById(R.id.btnLogout);

        // Lấy userId từ SharedPreferences
        userId = getUserId();

        if (userId == null) {
            // Nếu chưa đăng nhập, yêu cầu đăng nhập
            Toast.makeText(Profile_User.this, "Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Profile_User.this, Login_userActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Firebase references
        accountRef = FirebaseDatabase.getInstance().getReference("TaiKhoan").child(userId);
        customerRef = FirebaseDatabase.getInstance().getReference("KhachHang").child(userId);
        addressRef = FirebaseDatabase.getInstance().getReference("DiaChi").child(userId);

        // Lắng nghe sự kiện click nút Lưu
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmail = emailEditText.getText().toString().trim();
                String newUsername = usernameEditText.getText().toString().trim();
                String newPhone = phoneEditText.getText().toString().trim();
                String newAddress = addressEditText.getText().toString().trim();
                String newPassword = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                // Kiểm tra mật khẩu xác nhận
                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(Profile_User.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Cập nhật thông tin tài khoản (thay đổi từ "username" thành "tenTaiKhoan" và "password" thành "matKhau")
                Map<String, Object> accountUpdates = new HashMap<>();

                // Chỉ cập nhật tên tài khoản nếu người dùng nhập mới
                if (!newUsername.isEmpty()) {
                    accountUpdates.put("tenTaiKhoan", newUsername);  // Cập nhật tên tài khoản
                }

                // Chỉ cập nhật mật khẩu nếu người dùng nhập mới
                if (!newPassword.isEmpty()) {
                    accountUpdates.put("matKhau", newPassword);  // Cập nhật mật khẩu
                }

                // Cập nhật thông tin khách hàng
                Map<String, Object> customerUpdates = new HashMap<>();

                // Chỉ cập nhật email nếu người dùng nhập mới
                if (!newEmail.isEmpty()) {
                    customerUpdates.put("email", newEmail);
                }

                // Chỉ cập nhật số điện thoại nếu người dùng nhập mới
                if (!newPhone.isEmpty()) {
                    customerUpdates.put("sdt", newPhone);
                }

                // Cập nhật địa chỉ
                Map<String, Object> addressUpdates = new HashMap<>();

                // Chỉ cập nhật địa chỉ nếu người dùng nhập mới
                if (!newAddress.isEmpty()) {
                    addressUpdates.put("dia_chi", newAddress);
                }

                // Kiểm tra xem có trường nào cần cập nhật không
                if (accountUpdates.isEmpty() && customerUpdates.isEmpty() && addressUpdates.isEmpty()) {
                    Toast.makeText(Profile_User.this, "Không có thông tin nào được thay đổi!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Lưu thông tin tài khoản vào Firebase
                accountRef.updateChildren(accountUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Cập nhật thông tin khách hàng
                        customerRef.updateChildren(customerUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Cập nhật thông tin địa chỉ
                                addressRef.updateChildren(addressUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Hiển thị thông báo thành công và quay lại màn hình chính
                                        Toast.makeText(Profile_User.this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                                        finish(); // Đóng Activity hiện tại
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Profile_User.this, "Lỗi khi cập nhật địa chỉ!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Profile_User.this, "Lỗi khi cập nhật thông tin khách hàng!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Profile_User.this, "Lỗi khi cập nhật thông tin tài khoản!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Lắng nghe sự kiện click nút Quay lại
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Đóng Activity hiện tại
            }
        });

        // Lắng nghe sự kiện click nút Đăng xuất
        btnLogout.setOnClickListener(v -> {
            // Đăng xuất và xóa userId khỏi SharedPreferences
            SharedPreferences preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("userId");
            editor.apply();

            // Chuyển đến màn hình đăng nhập
            Intent intent = new Intent(Profile_User.this, Login_userActivity.class);
            startActivity(intent);
            finish(); // Đóng Activity hiện tại
        });
    }

    // Lấy userId từ SharedPreferences
    private String getUserId() {
        SharedPreferences preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        return preferences.getString("userId", null);  // Lấy userId đã lưu
    }
}
