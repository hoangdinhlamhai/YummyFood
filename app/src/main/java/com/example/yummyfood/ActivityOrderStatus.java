package com.example.yummyfood;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityOrderStatus extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);


        Button saveButton4 = findViewById(R.id.save_button4);
        saveButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCancelOrderDialog();
            }
        });


        Button btnBack = findViewById(R.id.btn_backToHomePage);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityOrderStatus.this, HomepageUserActivity.class);
                startActivity(intent);
            }
        });
    }


    private void showCancelOrderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityOrderStatus.this);
        builder.setTitle("Hủy đơn thành công")
                .setPositiveButton("Trang chủ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(ActivityOrderStatus.this, HomepageUserActivity.class);
                        startActivity(intent);
                    }
                })
                .setCancelable(false)
                .show();
    }
}
