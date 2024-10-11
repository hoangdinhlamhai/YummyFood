

package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.yummyfood.HomepageUserActivity;
import com.example.yummyfood.R;

    public class flashsale_user extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_flashsale_user);


            // Setup BottomNavigationView
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

            // Set up the event listener for navigation items
            bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();
                    if (itemId == R.id.navigation_home) {
                        // Chuyển về HomepageUserActivity khi nhấn vào nút Home
                        Intent intent = new Intent(flashsale_user.this, HomepageUserActivity.class);
                        startActivity(intent);
                        return true;
                    } else if (itemId == R.id.navigation_cart) {
                        // Xử lý khi nhấn nút Cart (có thể thêm xử lý tương tự nếu cần)
                        return true;
                    } else if (itemId == R.id.navigation_menu) {
                        // Xử lý khi nhấn nút Menu (có thể thêm xử lý tương tự nếu cần)
                        return true;
                    } else if (itemId == R.id.navigation_profile) {
                        // Xử lý khi nhấn nút Profile (có thể thêm xử lý tương tự nếu cần)
                        return true;
                    }
                    return false;
                }
            });
        }
    }
