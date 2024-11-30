package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Adapter.CategoryAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class category_user extends AppCompatActivity {

    private RecyclerView rvCategories;
    private ArrayList<String> categoryList;
    private ArrayList<Integer> categoryIdList;
    private CategoryAdapter categoryAdapter;
//    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_user);

//        DatabaseHelper databaseHelper = new DatabaseHelper(this);
//        databaseHelper.processCopy();

        // Open database
//        database = openOrCreateDatabase("dbYummyFood.db", MODE_PRIVATE, null);

        // Initialize RecyclerView
        rvCategories = findViewById(R.id.rvCategories); // Update ID in your XML
        rvCategories.setLayoutManager(new LinearLayoutManager(this));

        // Load categories
        categoryList = new ArrayList<>();
        categoryIdList = new ArrayList<>();
        fetchDataFromFirebase();

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

//    private void loadCategoriesFromDatabase() {
//        Cursor cursor = database.query("DanhMuc", new String[]{"idDanhMuc", "tenDanhMuc"}, null, null, null, null, null);
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                int idDanhMuc = cursor.getInt(cursor.getColumnIndex("idDanhMuc"));
//                String tenDanhMuc = cursor.getString(cursor.getColumnIndex("tenDanhMuc"));
//
//                categoryIdList.add(idDanhMuc); // Thêm ID danh mục
//                categoryList.add(tenDanhMuc); // Thêm tên danh mục
//            }
//            cursor.close();
//        }
//    }

    private void fetchDataFromFirebase() {
        // Kết nối đến Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DanhMuc");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xóa dữ liệu cũ
                categoryList.clear();
                categoryIdList.clear();

                // Duyệt qua từng danh mục
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        // Lấy ID (key) và tên danh mục (tenKV)
                        int id = Integer.parseInt(snapshot.getKey());
                        String tenKV = snapshot.child("tenDanhMuc").getValue(String.class);

                        // Thêm vào danh sách
                        categoryList.add(tenKV);
                        categoryIdList.add(id);
                    } catch (Exception e) {
                        Log.e("CategoryActivity", "Error parsing data", e);
                    }
                }

                // Thông báo Adapter cập nhật dữ liệu
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(category_user.this, "Failed to load data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
