package com.example.yummyfood;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Adapter.CategoryAdapter;
import com.example.yummyfood.Adapter.DatabaseHelper;
import com.example.yummyfood.Fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class category_user extends AppCompatActivity {

    private RecyclerView rvCategories;
    private ArrayList<String> categoryList;
    private ArrayList<Integer> categoryIdList;
    private CategoryAdapter categoryAdapter;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_user);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.processCopy();

        // Open database
        database = openOrCreateDatabase("dbYummyFood.db", MODE_PRIVATE, null);

        // Initialize RecyclerView
        rvCategories = findViewById(R.id.rvCategories); // Update ID in your XML
        rvCategories.setLayoutManager(new LinearLayoutManager(this));

        // Load categories
        categoryList = new ArrayList<>();
        categoryIdList = new ArrayList<>();
        loadCategoriesFromDatabase();

        // Set up adapter
        categoryAdapter = new CategoryAdapter(this, categoryList, categoryIdList);
        rvCategories.setAdapter(categoryAdapter);

        // Khởi tạo BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // Chọn item "Home" (giả sử ID của item "Home" là R.id.nav_home)
        bottomNav.setSelectedItemId(R.id.menu_nav1);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.home_nav1) {
                // Replace only the fragment for Home
                startActivity(new Intent(category_user.this, HomepageUserActivity.class));
//                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.menu_nav1) {
                // Start category_user Activity for Menu
//                selectedFragment = new ();
                //return true; // No fragment change here, handled by activity switch
            } else if (itemId == R.id.cart_nav1) {
                startActivity(new Intent(category_user.this, CartActivity.class));
                // Start category_user Activity for Menu
//                selectedFragment = new ();
                //return true; // No fragment change here, handled by activity switch
            } else if (itemId == R.id.person_nav1) {
                // Start profile_user Activity for User
                startActivity(new Intent(category_user.this, me_user.class));
                //return true; // No fragment change here, handled by activity switch
            }

            return true;
        });

    }

    private void loadCategoriesFromDatabase() {
        Cursor cursor = database.query("DanhMuc", new String[]{"idDanhMuc", "tenDanhMuc"}, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int idDanhMuc = cursor.getInt(cursor.getColumnIndex("idDanhMuc"));
                String tenDanhMuc = cursor.getString(cursor.getColumnIndex("tenDanhMuc"));

                categoryIdList.add(idDanhMuc); // Thêm ID danh mục
                categoryList.add(tenDanhMuc); // Thêm tên danh mục
            }
            cursor.close();
        }
    }
}
