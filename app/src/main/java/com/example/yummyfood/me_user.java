package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yummyfood.Fragment.MainActivityVoucher;
import com.example.yummyfood.Fragment.Voucher.voucher_item;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.yummyfood.Fragment.MainActivityVoucher;


public class me_user extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_user);

        // Khởi tạo BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.person_nav1);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home_nav1) {
                startActivity(new Intent(me_user.this, HomepageUserActivity.class));
//                finish();
            } else if (itemId == R.id.menu_nav1) {
                startActivity(new Intent(me_user.this, category_user.class));
//                finish();
            }else if (itemId == R.id.cart_nav1) {
                startActivity(new Intent(me_user.this, CartActivity.class));
//                finish();
            } else if (itemId == R.id.person_nav1) {
                return true;
            }
            return true;
        });

        // Thêm sự kiện khi nhấn vào lichsuban
        LinearLayout lichSuDat = findViewById(R.id.lichsuban);
        lichSuDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(me_user.this, history_table.class));
            }
        });

        // Thêm sự kiện khi nhấn vào choxacnhan
//        LinearLayout choxacnhan = findViewById(R.id.choxacnhan);
//        choxacnhan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(me_user.this, ListFoodUser.class));
//            }
//        });

        // Thêm sự kiện khi nhấn vào chogiaohang
//        LinearLayout chogiaohang = findViewById(R.id.chogiaohang);
//        chogiaohang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(me_user.this, ListFoodUser.class));
//            }
//        });

        // Thêm sự kiện khi nhấn vào danhgia
        LinearLayout danhgia = findViewById(R.id.danhgia);
        danhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(me_user.this, Rate_Order_User.class));
            }
        });

        // Thêm sự kiện khi nhấn vào xemthongtin
        LinearLayout xemthongtin = findViewById(R.id.xemthongtin);
        xemthongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(me_user.this, Profile_User.class));
            }
        });

        // Thêm sự kiện khi nhấn vào magiamgia
        LinearLayout magiamgia = findViewById(R.id.magiamgia);
        magiamgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(me_user.this, MainActivityVoucher.class));
            }
        });

        // Thêm sự kiện khi nhấn vào khuyenmai
        LinearLayout khuyenmai = findViewById(R.id.khuyenmai);
        khuyenmai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(me_user.this, flashsale_user.class));
            }
        });

        // Thêm sự kiện khi nhấn vào voucher
        LinearLayout voucher = findViewById(R.id.voucher);
        voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(me_user.this, flashsale_user.class));
            }
        });

        TextView viewHistoryOrder = findViewById(R.id.viewHistory);
        viewHistoryOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(me_user.this, Activity_History_Order.class));
            }
        });

    }
}
