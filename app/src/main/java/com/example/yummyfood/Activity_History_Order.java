package com.example.yummyfood;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Adapter.OrderHistoryAdapter;
import com.example.yummyfood.Domain.Order;
import com.example.yummyfood.Domain.OrderItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Activity_History_Order extends AppCompatActivity {
    private RecyclerView recyclerViewHistory;
    private OrderHistoryAdapter orderHistoryAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_oder_user);

        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference("HistoryOrders");

        loadOrderHistory();
    }

    private void loadOrderHistory() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<Order> orders = new ArrayList<>();

                // Lặp qua từng đơn hàng trong "HistoryOrders"
                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    String orderTime = orderSnapshot.child("orderTime").getValue(String.class);

                    // Lấy danh sách món ăn
                    List<OrderItem> items = new ArrayList<>();
                    DataSnapshot itemsSnapshot = orderSnapshot.child("items");
                    for (DataSnapshot itemSnapshot : itemsSnapshot.getChildren()) {
                        Map<String, Object> itemMap = (Map<String, Object>) itemSnapshot.getValue();
                        if (itemMap != null) {
                            String tenMonAn = (String) itemMap.get("tenMonAn");
                            int soLuong = ((Long) itemMap.get("soLuong")).intValue();
                            int giaMonAn = ((Long) itemMap.get("giaMonAn")).intValue();

                            items.add(new OrderItem(tenMonAn, soLuong, giaMonAn));
                        }
                    }

                    // Thêm đơn hàng vào danh sách
                    if (orderTime != null && !items.isEmpty()) {
                        orders.add(new Order(orderTime, items));
                    }
                }

                // Gán dữ liệu cho Adapter và RecyclerView
                orderHistoryAdapter = new OrderHistoryAdapter(Activity_History_Order.this, orders);
                recyclerViewHistory.setAdapter(orderHistoryAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(Activity_History_Order.this, "Lỗi tải lịch sử đơn hàng!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
