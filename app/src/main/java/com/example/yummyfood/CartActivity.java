package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Adapter.CartAdapter;
import com.example.yummyfood.Domain.CartItem;
import com.example.yummyfood.Domain.Food;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    private RecyclerView rvCart;
    private CartAdapter cartAdapter;
    private DatabaseReference databaseReference;
    private List<CartItem> chiTietMonAnList = new ArrayList<>();
    private Map<String, Food> monAnMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Khởi tạo BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.cart_nav1);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_nav1) {
                startActivity(new Intent(CartActivity.this, HomepageUserActivity.class));
            } else if (itemId == R.id.menu_nav1) {
                startActivity(new Intent(CartActivity.this, category_user.class));
            } else if (itemId == R.id.person_nav1) {
                startActivity(new Intent(CartActivity.this, me_user.class));
            }
            return true;
        });

        Button btnDatHang = findViewById(R.id.btnDatHang);
        btnDatHang.setOnClickListener(view -> {
            Intent intent = new Intent(CartActivity.this, ActivityPaymentUser.class);
            startActivity(intent);
        });

        rvCart = findViewById(R.id.rvCart);
        rvCart.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference();

        loadMonAnData();

        ImageView btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(view -> {
            List<CartItem> selectedItems = cartAdapter.getSelectedItems();
            if (selectedItems.isEmpty()) {
                Toast.makeText(CartActivity.this, "Chưa có mục nào được chọn để xóa!", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("ChiTietDonHang_MonAn");
            for (CartItem item : selectedItems) {
                cartRef.orderByChild("idMonAn").equalTo(item.getIdMonAn())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot data : snapshot.getChildren()) {
                                    data.getRef().removeValue()
                                            .addOnSuccessListener(aVoid -> Log.d("DeleteSuccess", "Xóa thành công!"))
                                            .addOnFailureListener(e -> Log.e("DeleteError", "Xóa thất bại: " + e.getMessage()));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e("DeleteError", "Lỗi khi xóa: " + error.getMessage());
                            }
                        });
            }

            chiTietMonAnList.removeAll(selectedItems);
            cartAdapter.notifyDataSetChanged();
            Toast.makeText(CartActivity.this, "Đã xóa các mục được chọn!", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadMonAnData() {
        databaseReference.child("MonAn").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String idMonAn = data.getKey();
                    String tenMonAn = data.child("tenMonAn").getValue(String.class);
                    int giaMonAn = data.child("donGia").getValue(Integer.class);
                    String hinhAnh = data.child("hinhAnh").getValue(String.class);

                    monAnMap.put(idMonAn, new Food(idMonAn, tenMonAn, null, giaMonAn, hinhAnh));
                }
                loadChiTietDonHang();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(CartActivity.this, "Lỗi tải dữ liệu món ăn!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadChiTietDonHang() {
        databaseReference.child("ChiTietDonHang_MonAn").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Integer idChiTietDonHang = data.child("idChiTietDonHang").getValue(Integer.class);
                    Integer idMonAn = data.child("idMonAn").getValue(Integer.class);
                    Integer soLuong = data.child("soLuong").getValue(Integer.class);

                    chiTietMonAnList.add(new CartItem(idChiTietDonHang != null ? idChiTietDonHang : 0,
                            idMonAn != null ? idMonAn : 0,
                            soLuong != null ? soLuong : 0));
                }

                cartAdapter = new CartAdapter(CartActivity.this, chiTietMonAnList, monAnMap);
                rvCart.setAdapter(cartAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(CartActivity.this, "Lỗi tải chi tiết đơn hàng!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}