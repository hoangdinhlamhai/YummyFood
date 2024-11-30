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

    private RecyclerView rvTableList; // RecyclerView để hiển thị danh sách bàn
    private List<Ban> tableList; // Danh sách các bàn
    private BanAdapter banAdapter; // Adapter để hiển thị danh sách bàn
    private DatabaseReference databaseReference; // Firebase Database Reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_ban); // Đảm bảo layout là activity_ds_ban

        // Khởi tạo RecyclerView
        rvTableList = findViewById(R.id.recyclerView);
        rvTableList.setLayoutManager(new LinearLayoutManager(this));

        // Lấy thông tin từ Intent
        String tenKhuVuc = getIntent().getStringExtra("tenKhuVuc"); // Lấy tên khu vực
        int idKhuVuc = getIntent().getIntExtra("idKhuVuc", -1); // Lấy ID khu vực

        // Hiển thị tên khu vực (ví dụ: "Tầng 1")
        TextView tenKhuVucTextView = findViewById(R.id.title);
        tenKhuVucTextView.setText(tenKhuVuc);

        // Khởi tạo danh sách bàn
        tableList = new ArrayList<>();

        // Tạo và gán adapter cho RecyclerView
        banAdapter = new BanAdapter(tableList);
        rvTableList.setAdapter(banAdapter);

        // Kết nối Firebase và lấy dữ liệu bàn từ khu vực
        databaseReference = FirebaseDatabase.getInstance().getReference("Ban");

        // Lắng nghe sự thay đổi dữ liệu trong Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Kiểm tra nếu có dữ liệu
                if (dataSnapshot.exists()) {
                    Log.d("dsBan", "Data exists for idKhuVuc: " + idKhuVuc);
                } else {
                    Log.d("dsBan", "No data found for idKhuVuc: " + idKhuVuc);
                }

                // Xóa dữ liệu cũ và thêm dữ liệu mới
                tableList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Ban ban = snapshot.getValue(Ban.class); // Lấy dữ liệu Ban từ Firebase
                    if (ban != null && ban.getIdKhuVuc() == idKhuVuc) {
                        tableList.add(ban); // Thêm bàn nếu ID khu vực trùng với khu vực đang xem
                    }
                }

                // Cập nhật lại RecyclerView
                banAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi khi tải dữ liệu
                Log.e("dsBan", "Error loading data", databaseError.toException());
            }
        });

        // Button quay lại
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Quay lại màn hình trước
            }
        });
    }
}
