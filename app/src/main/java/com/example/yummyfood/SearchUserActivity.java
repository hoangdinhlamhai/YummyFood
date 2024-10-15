package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        // Tìm ImageView bằng ID của icon_morong (imageView12)
        ImageView imageView = findViewById(R.id.imageView10);

        // Thiết lập sự kiện OnClickListener cho imageView
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng về HomepageUserActivity
                Intent intent = new Intent(SearchUserActivity.this, HomepageUserActivity.class);
                startActivity(intent);
            }
        });
    }
}
