package com.example.yummyfood;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.yummyfood.Fragment.HomeFragment;
import com.example.yummyfood.Fragment.MenuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Timer;
import java.util.TimerTask;

public class HomepageUserActivity extends AppCompatActivity {
    private Dialog dialog;
    private ViewPager viewPager;
    private int[] images = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};
    private int currentPageCounter = 0;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_user);

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });


        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new SliderAdapter(images, HomepageUserActivity.this));


        final Handler handler = new Handler();
        final Runnable update = () -> {
            if (currentPageCounter == images.length) {
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


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home_nav1);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
            bottomNavigationView.setSelectedItemId(R.id.home_nav1); // Set default selected item
        }


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.home_nav1) {
                // Replace only the fragment for Home
//                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.menu_nav1) {
                // Start category_user Activity for Menu
                startActivity(new Intent(HomepageUserActivity.this, category_user.class));
                //return true; // No fragment change here, handled by activity switch
            } else if (itemId == R.id.person_nav1) {
                // Start profile_user Activity for User
                startActivity(new Intent(HomepageUserActivity.this, Profile_User.class));
                //return true; // No fragment change here, handled by activity switch
            }


//            if (selectedFragment != null) {
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, selectedFragment)
//                        .commit();
//            }
            return true;
        });


        setupUIInteractions();
    }


    private void setupUIInteractions() {

        EditText searchEditText = findViewById(R.id.search);
        searchEditText.setOnClickListener(v -> startActivity(new Intent(HomepageUserActivity.this, SearchUserActivity.class)));


        TextView flashSaleTextView = findViewById(R.id.textView20);
        flashSaleTextView.setOnClickListener(v -> startActivity(new Intent(HomepageUserActivity.this, flashsale_user.class)));


        ImageView notificationIcon = findViewById(R.id.btntb);
        notificationIcon.setOnClickListener(v -> startActivity(new Intent(HomepageUserActivity.this, notification_user.class)));


        ImageView cartIcon1 = findViewById(R.id.cartIcon1);
        cartIcon1.setOnClickListener(v -> showDialog());


        ImageView foodImage1 = findViewById(R.id.foodImage2);
        foodImage1.setOnClickListener(v -> startActivity(new Intent(HomepageUserActivity.this, FoodRetailActivity.class)));


        ImageView cartIcon2 = findViewById(R.id.cartIcon2_homepage);
        cartIcon2.setOnClickListener(v -> startActivity(new Intent(HomepageUserActivity.this, CartActivity.class)));


        TextView tableBookingTextView = findViewById(R.id.textViewAll);
        tableBookingTextView.setOnClickListener(v -> startActivity(new Intent(HomepageUserActivity.this, booktable_usser.class)));
    }


    private void showDialog() {
        dialog = new Dialog(HomepageUserActivity.this);
        dialog.setContentView(R.layout.activity_dialog_outofstock_user);

        TextView exitButton = dialog.findViewById(R.id.dialog_exit);
        exitButton.setOnClickListener(v -> {
            dialog.dismiss(); // Close dialog
            startActivity(new Intent(HomepageUserActivity.this, HomepageUserActivity.class)); // Return to homepage
        });

        dialog.show();
    }
}
