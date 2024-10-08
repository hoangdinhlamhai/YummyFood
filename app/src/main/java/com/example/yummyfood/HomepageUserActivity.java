package com.example.yummyfood;

import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;
import java.util.Timer;
import java.util.TimerTask;

public class HomepageUserActivity extends AppCompatActivity {
    ViewPager viewPager;
    int image[] = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};
    int currentPageCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_user);

        // Setup window insets to adjust layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up the image slider
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new SliderAdapter(image, HomepageUserActivity.this));

        // Automatic image slider
        final Handler handler = new Handler();
        final Runnable update = () -> {
            if (currentPageCounter == image.length) {
                currentPageCounter = 0;
            }
            viewPager.setCurrentItem(currentPageCounter++, true);
        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 2500, 2500);

        // Setup BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    // Xử lý khi nhấn nút Home
                    return true;
                } else if (itemId == R.id.navigation_cart) {
                    // Xử lý khi nhấn nút Cart
                    return true;
                } else if (itemId == R.id.navigation_menu) {
                    // Xử lý khi nhấn nút Menu
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    // Xử lý khi nhấn nút Profile
                    return true;
                }
                return false;
            }
        });

    }
}
