package com.example.yummyfood;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class use_voucher_user extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_voucher_user);


        Button buttonUse = findViewById(R.id.use);
        buttonUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create AlertDialog
                new AlertDialog.Builder(use_voucher_user.this)
                        .setTitle("Áp dụng voucher thành công")
                        .setMessage("Bạn đã áp dụng voucher thành công.")
                        .setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent intent = new Intent(use_voucher_user.this, ActivityPaymentUser.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        });


        Button buttonChoose = findViewById(R.id.chon);
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create AlertDialog
                new AlertDialog.Builder(use_voucher_user.this)
                        .setTitle("Chọn voucher")
                        .setMessage("Bạn đã chọn voucher thành công.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent intent = new Intent(use_voucher_user.this, ActivityPaymentUser.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        });
    }
}
