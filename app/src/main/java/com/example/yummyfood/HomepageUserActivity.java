package com.example.yummyfood;

import android.app.Dialog;
import android.util.Log;
import android.view.View;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Button;
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
    Log log;
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
                    Intent intent = new Intent(HomepageUserActivity.this, HomepageUserActivity.class);
                    startActivity(intent);
                    return true;

                } else if (itemId == R.id.navigation_cart) {
                    Intent intent = new Intent(HomepageUserActivity.this, CartActivity.class);
                    startActivity(intent);
                    return true;

                } else if (itemId == R.id.navigation_menu) {
                    Intent intent = new Intent(HomepageUserActivity.this, category_user.class);
                    startActivity(intent);
                    return true;

                } else if (itemId == R.id.navigation_profile) {
                    // Chuyển sang activity_profile_user
                    Intent intent = new Intent(HomepageUserActivity.this, Profile_User.class);
                    startActivity(intent);
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
        ImageView imageView1 = findViewById(R.id.btntb);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("NotificationClick", "Icon thông báo được nhấn");
                Intent intent = new Intent(HomepageUserActivity.this, information_user.class);
                startActivity(intent);
            }
        });

        // thong bao het hang
        ImageView cartIcon1 = findViewById(R.id.cartIcon1);
        cartIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        // Xác định ImageView cho món ăn
        ImageView foodImage1 = findViewById(R.id.foodImage2);

        // Thiết lập OnClickListener cho ImageView
        foodImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang FoodRetailActivity khi nhấn vào ImageView
                Intent intent = new Intent(HomepageUserActivity.this, FoodRetailActivity.class);
                startActivity(intent);
            }
        });
         // sự kiện nhấn giỏ hàng thì chuyển đến trang giỏ hàng
        ImageView cartIcon = findViewById(R.id.cartIcon2_homepage);

        // Set sự kiện click cho ImageView
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang trang activity_cart
                Intent intent = new Intent(HomepageUserActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        //dat ban
        TextView txttable = findViewById(R.id.textViewAll);
        txttable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageUserActivity.this, booktable_usser.class);
                startActivity(intent);
            }
        });


    }

    // Hàm hiển thị dialog
    private void showDialog() {
        dialog = new Dialog(HomepageUserActivity.this);
        dialog.setContentView(R.layout.activity_dialog_outofstock_user);


        TextView exitButton = dialog.findViewById(R.id.dialog_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Đóng dialog
                Intent intent = new Intent(HomepageUserActivity.this, HomepageUserActivity.class);
                startActivity(intent); // Quay lại trang chủ
            }
        });

        dialog.show(); // Hiển thị dialog


        }

    }


