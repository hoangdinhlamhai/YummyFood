package com.example.yummyfood;

import android.app.Dialog;
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
    Dialog dialog;
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
                    // Chuyển về màn hình Home
                    Intent intent = new Intent(HomepageUserActivity.this, HomepageUserActivity.class);
                    startActivity(intent);
                    return true;

                } else if (itemId == R.id.navigation_cart) {
                    // Chuyển đến màn hình Cart (bạn có thể thêm Intent khác nếu cần)
                    return true;

                } else if (itemId == R.id.navigation_menu) {
                    // Chuyển đến màn hình danh mục (category_user)
                    Intent intent = new Intent(HomepageUserActivity.this, category_user.class);
                    startActivity(intent);
                    return true;

                } else if (itemId == R.id.navigation_profile) {
                    // Chuyển đến màn hình Profile (bạn có thể thêm Intent khác nếu cần)
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

        //chuyen sang thong bao
        ImageView imageView1 = findViewById(R.id.imageView4);
        imageView1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomepageUserActivity.this, information_user.class);
                startActivity(intent);
            }
        });


       // dialog.show();


//// Tìm TextView trong Dialog
//        TextView cart = dialog.findViewById(R.id.dialog_exit);
//        cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Chuyển về homepage
//                Intent intent = new Intent(dialog_outofstock_user.this, HomepageUserActivity.class);
//                startActivity(intent);
//            }
//        });
//
//// Hiển thị Dialog
//        dialog.show();
//
//        TextView loginTextView = dialog.findViewById(R.id.dialog_login);
//        loginTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Chuyển về Activity Login
//                Intent intent = new Intent(activity_register_user.this, Login_userActivity.class);
//                startActivity(intent);
//            }
//        });



    }
}
