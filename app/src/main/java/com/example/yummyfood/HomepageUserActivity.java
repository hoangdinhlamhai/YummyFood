package com.example.yummyfood;

import android.view.View;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

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
                    return true;
                } else if (itemId == R.id.navigation_cart) {
                    return true;
                } else if (itemId == R.id.navigation_menu) {
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    return true;
                }
                return false;
            }
        });

        // Setup sự kiện khi nhấn vào EditText search để chuyển sang SearchUserActivity
        EditText searchEditText = findViewById(R.id.search);
        searchEditText.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageUserActivity.this, SearchUserActivity.class);
            startActivity(intent);
        });

        // Setup sự kiện khi nhấn vào TextView Flashsale để chuyển sang FlashsaleUserActivity
        TextView textView = findViewById(R.id.textView20);
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomepageUserActivity.this, flashsale_user.class);
                startActivity(intent);
            }
        });
        ImageView imageView = findViewById(R.id.imageView4);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomepageUserActivity.this, information_user.class);
                startActivity(intent);
            }
        });




    }
}
