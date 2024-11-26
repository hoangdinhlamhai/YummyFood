package com.example.yummyfood;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Adapter.DatabaseHelper;
import com.example.yummyfood.Adapter.ListFoodAdapter;
import com.example.yummyfood.Domain.Food;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class List_Food extends AppCompatActivity {
    private RecyclerView rvFoodList;
    private List<Food> foodList;
    private ListFoodAdapter foodAdapter;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);



        // Khởi tạo RecyclerView
        rvFoodList = findViewById(R.id.rvFoodList);
        rvFoodList.setLayoutManager(new LinearLayoutManager(this));

        // Mở cơ sở dữ liệu
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.processCopy();
        database = openOrCreateDatabase("dbYummyFood.db", MODE_PRIVATE, null);

        // Lấy idDanhMuc từ Intent
        int categoryId = getIntent().getIntExtra("idDanhMuc", -1);
        // Nhận tên danh mục từ Intent
        String categoryName = getIntent().getStringExtra("tenDanhMuc");

        //hiển thị trong một TextView
        TextView titleTextView = findViewById(R.id.title); // Đảm bảo TextView này tồn tại trong layout
        if (titleTextView != null) {
            titleTextView.setText(categoryName);
        }

        // Load danh sách món ăn
        foodList = new ArrayList<>();
        loadFoodsFromDatabase(categoryId);

        // Thiết lập Adapter
        foodAdapter = new ListFoodAdapter(this, foodList);
        rvFoodList.setAdapter(foodAdapter);

        //back to homepage
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadFoodsFromDatabase(int categoryId) {
        Cursor cursor = database.query(
                "MonAn",
                null,
                "idDanhMuc = ?",
                new String[]{String.valueOf(categoryId)},
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Lấy dữ liệu từ cột trong bảng "MonAn"
                int id = cursor.getInt(cursor.getColumnIndex("idMonAn"));
                String name = cursor.getString(cursor.getColumnIndex("tenMonAn"));
                String description = cursor.getString(cursor.getColumnIndex("moTa")); // Mô tả
                int price = cursor.getInt(cursor.getColumnIndex("donGia")); // Giá
                int quantity = cursor.getInt(cursor.getColumnIndex("soLuong")); // Số lượng
                byte[] image = cursor.getBlob(cursor.getColumnIndex("hinhAnh")); // Hình ảnh

                // Tạo đối tượng Food
                Food food = new Food(id, name, description, price, quantity, image);

                // Thêm vào danh sách
                foodList.add(food);
            }
            cursor.close();
        }
    }



}