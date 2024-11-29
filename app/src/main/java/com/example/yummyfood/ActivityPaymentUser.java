package com.example.yummyfood;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityPaymentUser extends AppCompatActivity {
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_user);
        // Đảm bảo layout của bạn được thiết lập

        Button btnReturnRetailFood = findViewById(R.id.btn_paying_payment);

        // Thiết lập sự kiện nhấn nút
        btnReturnRetailFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển về HomepageUserActivity
                Intent intent = new Intent(ActivityPaymentUser.this, FoodDetailActivity.class);
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
        // Khởi tạo nút btn_paying_payment
        Button btnPayingPayment = findViewById(R.id.btn_paying_payment);

        // Thiết lập sự kiện nhấn nút thanh toán
        btnPayingPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến ActivityOrderStatus
                Intent intent = new Intent(ActivityPaymentUser.this, scan_qr.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView btnBack = findViewById(R.id.btn_return_payment);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView voucher = findViewById(R.id.voucher);
        voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityPaymentUser.this, use_voucher_user.class);
                startActivity(intent);
            }
        });




    }
}
