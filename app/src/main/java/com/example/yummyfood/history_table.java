package com.example.yummyfood;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Adapter.TableHistoryAdapter;
import com.example.yummyfood.Domain.ChiTietDatBan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class history_table extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TableHistoryAdapter adapter;
    private List<ChiTietDatBan> bookingList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_table);

        recyclerView = findViewById(R.id.recyclerViewBookingHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TableHistoryAdapter(bookingList, this::cancelBooking);
        recyclerView.setAdapter(adapter);

        loadBookingData();

        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(view -> finish());
    }


    private void loadBookingData() {
        DatabaseReference bookingRef = FirebaseDatabase.getInstance().getReference("ChiTietDatBan");

        bookingRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                bookingList.clear(); // Xóa danh sách cũ trước khi tải mới
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    ChiTietDatBan booking = snapshot.getValue(ChiTietDatBan.class);

                    // Đặt ID từ Firebase vào đối tượng (nếu cần xóa/hủy sau này)
                    if (booking != null) {
                        booking.setId(snapshot.getKey());
                        bookingList.add(booking);
                    }
                }
                adapter.notifyDataSetChanged(); // Cập nhật RecyclerView
            } else {
                Toast.makeText(this, "Lỗi khi tải dữ liệu: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void cancelBooking(ChiTietDatBan booking) {
        DatabaseReference bookingRef = FirebaseDatabase.getInstance().getReference("ChiTietDatBan");
        bookingRef.child(booking.getId()).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                booking.setTrangThai("Đã hủy");
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Đã hủy đặt bàn thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Hủy đặt bàn thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
