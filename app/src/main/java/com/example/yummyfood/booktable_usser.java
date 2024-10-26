package com.example.yummyfood;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.LinearLayout;

public class booktable_usser extends AppCompatActivity {

    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booktable_usser);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // thong bao hết ban
        LinearLayout linearLayout = findViewById(R.id.ban1tang2);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        //xem chi tiet thong tin ban
        LinearLayout linearLayout1 = findViewById(R.id.ban1tang1);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(booktable_usser.this, detail_table.class);
                startActivity(intent);
            }
        });
//ve trang chu
        Button btnb = findViewById(R.id.btnBack);
        btnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(booktable_usser.this, HomepageUserActivity.class);
                startActivity(intent);
            }
        });


    }

    // Hàm hiển thị dialog
    private void showDialog() {
        dialog = new Dialog(booktable_usser.this);
        dialog.setContentView(R.layout.activity_dialog_bookedtable_user);


        TextView exitButton = dialog.findViewById(R.id.dialog_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Đóng dialog
                Intent intent = new Intent(booktable_usser.this, booktable_usser.class);
                startActivity(intent); // Quay lại trang chủ
            }
        });

        dialog.show(); // Hiển thị dialog
    }
}