package com.example.yummyfood;

import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homepage_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new SliderAdapter(image, HomepageUserActivity.this));

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPageCounter == image.length) {
                    currentPageCounter = 0;
                }
                viewPager.setCurrentItem(currentPageCounter++, true);
            }
        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 2500, 2500);
    }

}
