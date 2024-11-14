package com.example.yummyfood;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

public class Activity_History_Order extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_oder_user);
        Button btnReturnHistoryOrder = findViewById(R.id.btn_return_historyorder);

        // Thiết lập sự kiện nhấn nút
        btnReturnHistoryOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển về HomepageUserActivity
//                Intent intent = new Intent(Activity_History_Order.this, ActivityOrderStatus.class);
//                startActivity(intent);
                finish(); // Kết thúc FoodRetailActivity để không trở lại được bằng nút quay lại
            }
        });
    }
}
