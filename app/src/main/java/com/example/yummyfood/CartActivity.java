package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Khởi tạo BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // Chọn item "Home" (giả sử ID của item "Home" là R.id.nav_home)
        bottomNav.setSelectedItemId(R.id.cart_nav1);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.home_nav1) {
                // Replace only the fragment for Home
                startActivity(new Intent(CartActivity.this, HomepageUserActivity.class));
//                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.menu_nav1) {
                // Start category_user Activity for Menu
                startActivity(new Intent(CartActivity.this, category_user.class));
//                selectedFragment = new ();
                //return true; // No fragment change here, handled by activity switch
            } else if (itemId == R.id.person_nav1) {
                // Start profile_user Activity for User
                startActivity(new Intent(CartActivity.this, me_user.class));
                //return true; // No fragment change here, handled by activity switch
            }

            return true;
        });

        setupUIInteractions();
    }


    private void setupUIInteractions() {
        LinearLayout mon1 = findViewById(R.id.mon1);
        LinearLayout mon2 = findViewById(R.id.mon2);
        LinearLayout mon3 = findViewById(R.id.mon3);
        Button btn_paying3 = findViewById(R.id.btn_paying3);


        mon1.setOnClickListener(v -> startActivity(new Intent(CartActivity.this, FoodDetailActivity.class)));
        mon2.setOnClickListener(v -> startActivity(new Intent(CartActivity.this, FoodDetailActivity.class)));
        mon3.setOnClickListener(v -> startActivity(new Intent(CartActivity.this, FoodDetailActivity.class)));
        btn_paying3.setOnClickListener(v -> startActivity(new Intent(CartActivity.this, ActivityPaymentUser.class)));
    }
}
