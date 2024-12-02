package com.example.yummyfood;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class scan_qr extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        Button btnTransfer = findViewById(R.id.btn_transfer);
        Button btnTienMach = findViewById(R.id.btn_cash);


        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSuccessDialog("Thanh toán thành công", "Xem trạng thái", true);
            }
        });


        btnTienMach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSuccessDialog("Đặt hàng thành công", "Xem trạng thái", false);
            }
        });

    }


    private void showSuccessDialog(String title, String positiveButtonText, boolean isTransfer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(scan_qr.this);
        builder.setTitle(title)
                .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(scan_qr.this, ActivityOrderStatus.class);
                        startActivity(intent);
                    }
                })
                .setCancelable(false)
                .show();
    }
}
