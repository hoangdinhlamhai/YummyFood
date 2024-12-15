package com.example.yummyfood;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class SearchUserActivity extends AppCompatActivity {

    private EditText searchEditText;
    private RecyclerView recyclerView;
    private List<Food> fullFoodList; // Danh sách đầy đủ
    private List<Food> filteredFoodList; // Danh sách sau khi lọc
    private ListFoodAdapter foodAdapter;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        EditText searchEditText = findViewById(R.id.editTextText4);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        TextView tvNoResults = findViewById(R.id.tvNoResults);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Food> fullFoodList = new ArrayList<>();
        List<Food> filteredFoodList = new ArrayList<>();
        ListFoodAdapter foodAdapter = new ListFoodAdapter(this, filteredFoodList);
        recyclerView.setAdapter(foodAdapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MonAn");

        // Lấy dữ liệu từ Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fullFoodList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String idMonAn = data.getKey();
                    String tenMonAn = data.child("tenMonAn").getValue(String.class);
                    int donGia = data.child("donGia").getValue(Integer.class);
                    String moTa = data.child("moTa").getValue(String.class);
                    String hinhAnh = data.child("hinhAnh").getValue(String.class);

                    Food food = new Food(idMonAn, tenMonAn, moTa, donGia, hinhAnh);
                    fullFoodList.add(food);
                }
                filteredFoodList.clear();
                filteredFoodList.addAll(fullFoodList);
                foodAdapter.notifyDataSetChanged();

                // Hiển thị/hide TextView nếu danh sách rỗng
                if (filteredFoodList.isEmpty()) {
                    tvNoResults.setVisibility(View.VISIBLE);
                } else {
                    tvNoResults.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Lỗi Firebase: " + error.getMessage());
            }
        });

        // Lọc danh sách khi nhập từ khóa
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filteredFoodList.clear();
                for (Food food : fullFoodList) {
                    if (food.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                        filteredFoodList.add(food);
                    }
                }
                foodAdapter.notifyDataSetChanged();

                // Hiển thị/hide TextView nếu danh sách rỗng
                if (filteredFoodList.isEmpty()) {
                    tvNoResults.setVisibility(View.VISIBLE);
                } else {
                    tvNoResults.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}

