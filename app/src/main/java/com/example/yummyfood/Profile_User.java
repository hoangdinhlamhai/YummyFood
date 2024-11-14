package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile_User extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_user);

//        // Khởi tạo BottomNavigationView
//        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
//
//        // Chọn item "Home" (giả sử ID của item "Home" là R.id.nav_home)
//        bottomNav.setSelectedItemId(R.id.person_nav1);
//
//        bottomNav.setOnNavigationItemSelectedListener(item -> {
//            Fragment selectedFragment = null;
//            int itemId = item.getItemId();
//
//            if (itemId == R.id.home_nav1) {
//                // Replace only the fragment for Home
//                startActivity(new Intent(Profile_User.this, HomepageUserActivity.class));
////                selectedFragment = new HomeFragment();
//            } else if (itemId == R.id.menu_nav1) {
//                // Start category_user Activity for Menu
//                startActivity(new Intent(Profile_User.this, category_user.class));
////                selectedFragment = new ();
//                //return true; // No fragment change here, handled by activity switch
//            } else if (itemId == R.id.person_nav1) {
//                // Start profile_user Activity for User
////                startActivity(new Intent(Profile_User.this, Profile_User.class));
//                //return true; // No fragment change here, handled by activity switch
//            }
//
//            return true;
//        });
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}