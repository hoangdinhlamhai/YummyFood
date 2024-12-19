package com.example.yummyfood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Adapter.OrderstatusAdapter;

import com.example.yummyfood.Domain.OrderItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityOrderStatus extends AppCompatActivity {

    private RecyclerView recyclerViewStatus;
    private OrderstatusAdapter orderStatusAdapter;
    private List<OrderItem> orderItemList;
    private TextView diachiTextView; // TextView để hiển thị thông tin địa chỉ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        Button home = findViewById(R.id.save_button4);
        home.setOnClickListener(v -> startActivity(new Intent(ActivityOrderStatus.this, HomepageUserActivity.class)));


        // Ánh xạ TextView để hiển thị thông tin địa chỉ
        diachiTextView = findViewById(R.id.diachi);

        // Nút back
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(view -> finish());

        // Ánh xạ RecyclerView
        recyclerViewStatus = findViewById(R.id.recyclerViewstatus);
        recyclerViewStatus.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo danh sách và adapter
        orderItemList = new ArrayList<>();
        orderStatusAdapter = new OrderstatusAdapter(this, orderItemList);
        recyclerViewStatus.setAdapter(orderStatusAdapter);

        // Lấy userId từ SharedPreferences hoặc Intent (tuỳ thuộc vào cách bạn lưu thông tin người dùng)
        SharedPreferences preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String userId = preferences.getString("userId", null);

        if (userId != null) {
            loadCustomerData(userId);
        }

        // Gọi hàm load đơn hàng mới nhất
        loadLatestOrder();
    }

    // Tải thông tin khách hàng từ Firebase
    private void loadCustomerData(String userId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        // Lấy dữ liệu khách hàng
        databaseReference.child("KhachHang").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String customerName = snapshot.child("tenKhachHang").getValue(String.class);
                    String customerPhone = snapshot.child("sdt").getValue(String.class);

                    // Tải địa chỉ của khách hàng
                    loadAddressData(userId, customerName, customerPhone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivityOrderStatus.this, "Lỗi khi tải dữ liệu khách hàng!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Lấy địa chỉ của khách hàng và hiển thị vào TextView
    private void loadAddressData(String userId, String customerName, String customerPhone) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("DiaChi").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String address = snapshot.child("dia_chi").getValue(String.class);
                    if (address != null) {
                        String fullAddress = customerName + " | " + customerPhone + "\n" + address;
                        diachiTextView.setText(fullAddress);  // Hiển thị thông tin vào TextView
                    } else {
                        Toast.makeText(ActivityOrderStatus.this, "Không tìm thấy địa chỉ!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActivityOrderStatus.this, "Địa chỉ không tồn tại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivityOrderStatus.this, "Lỗi khi tải dữ liệu địa chỉ!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Hàm tải đơn hàng mới nhất
    private void loadLatestOrder() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ChiTietDonHang");

        databaseReference.orderByChild("orderTime")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        orderItemList.clear(); // Xóa danh sách cũ
                        DataSnapshot lastOrderSnapshot = null;

                        for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                            lastOrderSnapshot = orderSnapshot; // Lấy đơn hàng cuối cùng
                        }

                        if (lastOrderSnapshot != null) {
                            for (DataSnapshot itemSnapshot : lastOrderSnapshot.child("items").getChildren()) {
                                String tenMonAn = itemSnapshot.child("tenMonAn").getValue(String.class);
                                Integer giaMonAn = itemSnapshot.child("giaMonAn").getValue(Integer.class);
                                Integer soLuong = itemSnapshot.child("soLuong").getValue(Integer.class);
                                String hinhAnh = itemSnapshot.child("hinhAnh").getValue(String.class);

                                if (tenMonAn != null && giaMonAn != null && soLuong != null) {
                                    orderItemList.add(new OrderItem(tenMonAn, soLuong, giaMonAn, hinhAnh));
                                }
                            }
                        }

                        orderStatusAdapter.notifyDataSetChanged(); // Cập nhật adapter
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ActivityOrderStatus.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
