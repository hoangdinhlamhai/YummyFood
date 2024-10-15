package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class category_user extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_user);

        // Áp dụng insets cho giao diện
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    // Chuyển về màn hình Home
                    Intent intent = new Intent(category_user.this, HomepageUserActivity.class);
                    startActivity(intent);
                    return true;

                } else if (itemId == R.id.navigation_cart) {
                    // Chuyển đến màn hình Cart (bạn có thể thêm Intent khác nếu cần)
                    return true;

                } else if (itemId == R.id.navigation_menu) {
                    // Chuyển đến màn hình danh mục (category_user)
                    Intent intent = new Intent(category_user.this, category_user.class);
                    startActivity(intent);
                    return true;

                } else if (itemId == R.id.navigation_profile) {
                    // Chuyển đến màn hình Profile (bạn có thể thêm Intent khác nếu cần)
                    return true;
                }

                return false;
            }
        });
    }
}
