

package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

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

            // Tìm ImageView bằng ID của icon_morong (imageView12)
            ImageView imageView = findViewById(R.id.imageView10);

            // Thiết lập sự kiện OnClickListener cho imageView
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển hướng về HomepageUserActivity
                    Intent intent = new Intent(flashsale_user.this, HomepageUserActivity.class);
                    startActivity(intent);
                }
            });



        }
    }
