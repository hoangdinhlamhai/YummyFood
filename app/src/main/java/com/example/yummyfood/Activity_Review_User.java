package com.example.yummyfood;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_Review_User extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_user);

        Button btnSaveReview = findViewById(R.id.btn_save_review);

        btnSaveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị AlertDialog
                new AlertDialog.Builder(Activity_Review_User.this)
                        .setTitle("Đánh giá thành công")
                        .setMessage("Cảm ơn bạn đã đánh giá món ăn. Chúc bạn có trải nghiệm tuyệt vời!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Chuyển sang giao diện history_order
                                Intent intent = new Intent(Activity_Review_User.this, Activity_History_Order.class);
                                startActivity(intent);
                                finish(); // Đóng Activity hiện tại
                            }
                        })
                        .show();
            }
        });

    }
}
