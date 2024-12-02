package com.example.yummyfood;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_register_user extends AppCompatActivity {

    EditText signupName, signupEMail, signupUsername, signupPassword;
    TextView loginRedirecText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    Dialog dialog;

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
                reference = database.getReference("users");

                String name = signupName.getText().toString();
                String email = signupEMail.getText().toString();
                String username = signupUsername.getText().toString();
                String pass = signupPassword.getText().toString();

                HelperClass helperClass = new HelperClass(name, email, username, pass);

                // Sử dụng username thay vì name làm child key
                reference.child(username).setValue(helperClass);
                Toast.makeText(activity_register_user.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity_register_user.this, Login_userActivity.class);
                startActivity(intent);
            }
        });


        loginRedirecText.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View view){
              Intent intent = new Intent(activity_register_user.this, Login_userActivity.class);
              startActivity(intent);

        }
    });



        // Tạo Dialog
//        dialog = new Dialog((activity_register_user.this));
//        dialog.setContentView(R.layout.activity_dialog_create_account);
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_createaccount));
//        dialog.setCancelable(true);  // Bạn có thể nhấn ra ngoài để tắt Dialog nếu muốn
//
//        // Thiết lập Edge to Edge cho layout
//        EdgeToEdge.enable(this);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        // Nút "Create Account"
//        Button createAccountButton = findViewById(R.id.btn_create_user);
//        createAccountButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Hiển thị Dialog khi nhấn vào nút "Create Account"
//                dialog.show();
//            }
//        });
//
//        // Nút "Already Have an Account" để chuyển sang màn hình đăng nhập
//        TextView bt = findViewById(R.id.txt_w_create);
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(activity_register_user.this, Login_userActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        // Tìm TextView trong Dialog và thiết lập sự kiện cho nó
//        TextView loginTextView = dialog.findViewById(R.id.dialog_login);
//        loginTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Chuyển về Activity Login
//                Intent intent = new Intent(activity_register_user.this, Login_userActivity.class);
//                startActivity(intent);
//            }
//        });


    }
}
