package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        // Chọn item "Home" (giả sử ID của item "Home" là R.id.nav_home)
        bottomNav.setSelectedItemId(R.id.cart_nav1);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.home_nav1) {
                // Replace only the fragment for Home
                startActivity(new Intent(CartActivity.this, HomepageUserActivity.class));
//                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.menu_nav1) {
                // Start category_user Activity for Menu
                startActivity(new Intent(CartActivity.this, category_user.class));
//                selectedFragment = new ();
                //return true; // No fragment change here, handled by activity switch
            } else if (itemId == R.id.person_nav1) {
                // Start profile_user Activity for User
                startActivity(new Intent(CartActivity.this, me_user.class));
                //return true; // No fragment change here, handled by activity switch
            }

            return true;
        });

        Button btnDatHang = findViewById(R.id.btnDatHang);
        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, ActivityPaymentUser.class);
                startActivity(intent);
            }
        });

        // Initialize RecyclerView
        rvCart = findViewById(R.id.rvCart);
        rvCart.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Load data from Firebase
        loadMonAnData();

        //xoá món trong cart
        ImageView btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(view -> {
            List<CartItem> selectedItems = cartAdapter.getSelectedItems(); // Lấy các mục được chọn

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

            // Cập nhật giao diện
            chiTietMonAnList.removeAll(selectedItems);
            cartAdapter.notifyDataSetChanged();
            Toast.makeText(CartActivity.this, "Đã xóa các mục được chọn!", Toast.LENGTH_SHORT).show();
        });


    }

    private void loadMonAnData() {
        // Load MonAn data
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

                // Load ChiTietDonHang_MonAn after MonAn data
                loadChiTietDonHang();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(CartActivity.this, "Lỗi tải dữ liệu món ăn!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadChiTietDonHang() {
        // Load ChiTietDonHang_MonAn data
        databaseReference.child("ChiTietDonHang_MonAn").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    /// Lấy idChiTietDonHang
                    Integer idChiTietDonHang = data.child("idChiTietDonHang").getValue(Integer.class);
                    idChiTietDonHang = (idChiTietDonHang != null) ? idChiTietDonHang : 0;

                    // Lấy idMonAn
                    Integer idMonAn = data.child("idMonAn").getValue(Integer.class);
                    idMonAn = (idMonAn != null) ? idMonAn : 0;

                    // Lấy soLuong
                    Integer soLuong = data.child("soLuong").getValue(Integer.class);
                    soLuong = (soLuong != null) ? soLuong : 0;

                    // Kiểm tra null trước khi thêm vào danh sách
//                    if (idChiTietDonHang > 0 && idMonAn > 0 && soLuong > 0) { // Hoặc kiểm tra theo điều kiện bạn muốn
                        chiTietMonAnList.add(new CartItem(idChiTietDonHang, idMonAn, soLuong));
//                    } else {
//                        Log.w("ChiTietDebug", "Dữ liệu không đầy đủ: idChiTietDonHang=" + idChiTietDonHang + ", idMonAn=" + idMonAn + ", soLuong=" + soLuong);
//                    }
                }

                // Set data to RecyclerView adapter
                cartAdapter = new CartAdapter(CartActivity.this, chiTietMonAnList, monAnMap);
                rvCart.setAdapter(cartAdapter);
                //monAnMap đã có được các object Food
//                for (Map.Entry<String, Food> entry : monAnMap.entrySet()) {
//                    Log.d("MonAnMap", "Key: " + entry.getKey() + ", Value: " + entry.getValue().toString());
//                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(CartActivity.this, "Lỗi tải chi tiết đơn hàng!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
