package com.example.yummyfood.admin;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.yummyfood.R;
import com.example.yummyfood.admin.list_food.List_Food;

public class HomePage_Admin extends AppCompatActivity {

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //addFood
        LinearLayout linearLayout = findViewById(R.id.addFoodLinearLayout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage_Admin.this, Add_Food.class);
                startActivity(intent);
            }
        });

        //listFood
        LinearLayout listFood = findViewById(R.id.listFoodLinearLayout);
        listFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage_Admin.this, List_Food.class);
                startActivity(intent);
            }
        });

        //addUser
        LinearLayout addUser = findViewById(R.id.addUserLinearLayout);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage_Admin.this, Add_User.class);
                startActivity(intent);
            }
        });

        //listUser
        LinearLayout listUser = findViewById(R.id.listUserLinearLayout);
        listUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage_Admin.this, List_User.class);
                startActivity(intent);
            }
        });

        //confirm
        LinearLayout confirm = findViewById(R.id.confirmLinearLayout);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage_Admin.this, List_Confirm_Order.class);
                startActivity(intent);
            }
        });

        //profile
        LinearLayout pr5 = findViewById(R.id.profileLinearLayout);
        pr5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage_Admin.this, Profile_Admin.class);
                startActivity(intent);
            }
        });

        //confirm logout
        dialog = new Dialog(HomePage_Admin.this);
        dialog.setContentView(R.layout.activity_dialog_confirm_logout_admin);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_createaccount));
        dialog.setCancelable(false);

        LinearLayout logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }
}