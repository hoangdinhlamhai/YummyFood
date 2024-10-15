package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityPaymentUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_user);
        // Đảm bảo layout của bạn được thiết lập


        // Khởi tạo nút btn_return_retailfood
        Button btnReturnRetailFood = findViewById(R.id.btn_return_payment);

        // Thiết lập sự kiện nhấn nút
        btnReturnRetailFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển về HomepageUserActivity
                Intent intent = new Intent(ActivityPaymentUser.this, FoodRetailActivity.class);
                startActivity(intent);
                finish(); // Kết thúc FoodRetailActivity để không trở lại được bằng nút quay lại
            }
        });
        TextView txt1 = findViewById(R.id.textView12);

// Thiết lập sự kiện nhấn vào TextView
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang edit_address khi TextView được nhấn
                Intent intent = new Intent(ActivityPaymentUser.this, edit_address.class);
                startActivity(intent);
                finish(); // Kết thúc ActivityPaymentUser để không quay lại bằng nút back
            }
        });

    }
}
