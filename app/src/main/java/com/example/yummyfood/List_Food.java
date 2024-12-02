package com.example.yummyfood;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Adapter.ListFoodAdapter;
import com.example.yummyfood.Domain.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class List_Food extends AppCompatActivity {
    private RecyclerView rvFoodList;
    private List<Food> foodList;
    private ListFoodAdapter foodAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);

        // Khởi tạo RecyclerView
        rvFoodList = findViewById(R.id.rvFoodList);
        rvFoodList.setLayoutManager(new LinearLayoutManager(this));

        // Lấy idDanhMuc từ Intent
        int categoryId = getIntent().getIntExtra("idDanhMuc", -1);
        // Nhận tên danh mục từ Intent
        String categoryName = getIntent().getStringExtra("tenDanhMuc");

        //hiển thị trong một TextView
        TextView titleTextView = findViewById(R.id.title); // Đảm bảo TextView này tồn tại trong layout
        if (titleTextView != null) {
            titleTextView.setText(categoryName);
        }

        foodList = new ArrayList<>(); // Khởi tạo danh sách rỗng trước
        // Thiết lập Adapter sau
        foodAdapter = new ListFoodAdapter(this, foodList);
        rvFoodList.setAdapter(foodAdapter);

        // Kết nối Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("MonAn");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodList.clear();

                for (DataSnapshot data : snapshot.getChildren()) {
                    int idDM = data.child("idDanhMuc").getValue(Integer.class);
                    if (idDM == categoryId) {
                        String idMonAn = data.getKey();
                        String tenMonAn = data.child("tenMonAn").getValue(String.class);
                        int donGia = data.child("donGia").getValue(Integer.class);
                        String moTa = data.child("moTa").getValue(String.class);
                        String hinhAnh = data.child("hinhAnh").getValue(String.class);

                        Food food = new Food(idMonAn, tenMonAn, moTa, donGia, hinhAnh);
                        foodList.add(food);
                    }
                }

                foodAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi
                Log.e("FirebaseError", "Lỗi Firebase: " + error.getMessage());
            }
        });


        //back to homepage
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}