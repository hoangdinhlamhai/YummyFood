package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityOrderStatus  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        // Xử lý giao diện  ở đây
        // Khởi tạo nút btn_see_history_orderstatus
        //Button btnSeeHistoryOrder = findViewById(R.id.btn_see_history_orderstatus);

        // Thiết lập sự kiện nhấn nút
//        btnSeeHistoryOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Chuyển về Trang ca nhan, xem lich su don hang
//                Intent intent = new Intent(ActivityOrderStatus.this, me_user.class);
//                startActivity(intent);
//                finish(); // Kết thúc FoodRetailActivity để không trở lại được bằng nút quay lại
//            }
//        });

        //back to homepage
        Button btnBack = findViewById(R.id.btn_backToHomePage);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityOrderStatus.this, HomepageUserActivity.class);
                startActivity(intent);
//                finish();
            }
        });
    }
}
