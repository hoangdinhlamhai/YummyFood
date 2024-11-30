package com.example.yummyfood;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Adapter.BanAdapter;
import com.example.yummyfood.Domain.Ban;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class dsBan extends AppCompatActivity {

    private RecyclerView rvTableList;
    private List<Ban> tableList;
    private BanAdapter banAdapter;
    private DatabaseReference databaseReference;
    private TextView tenKhuVucTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_ban);

        // Lấy thông tin từ Intent
        String tenKhuVuc = getIntent().getStringExtra("tenKhuVuc");
        String idKhuVuc = getIntent().getStringExtra("idKhuVuc");

        // Set tên khu vực
        tenKhuVucTextView = findViewById(R.id.title);
        tenKhuVucTextView.setText(tenKhuVuc);

        // Khởi tạo RecyclerView
        rvTableList = findViewById(R.id.recyclerView);
        rvTableList.setLayoutManager(new LinearLayoutManager(this));
        rvTableList.setHasFixedSize(true);

        // Khởi tạo danh sách bàn
        tableList = new ArrayList<>();
        banAdapter = new BanAdapter(tableList);
        rvTableList.setAdapter(banAdapter);

        // Khởi tạo DatabaseReference
        databaseReference = FirebaseDatabase.getInstance().getReference("Ban");

        // Lấy dữ liệu bàn từ Firebase theo idKhuVuc
        loadBanData(idKhuVuc);
    }

    private void loadBanData(String idKhuVuc) {
        // Lọc theo idKhuVuc
        databaseReference.orderByChild("idKhuVuc").equalTo(idKhuVuc).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tableList.clear();  // Xóa danh sách trước khi thêm mới
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Ban ban = snapshot.getValue(Ban.class);
                    if (ban != null) {
                        tableList.add(ban);  // Thêm bàn vào danh sách
                    }
                }
                banAdapter.notifyDataSetChanged();  // Cập nhật adapter
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("dsBan", "Error loading data", databaseError.toException());
            }
        });
    }
}
