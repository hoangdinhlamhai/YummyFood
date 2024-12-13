package com.example.yummyfood;

import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Adapter.FoodPaymentAdapter;
import com.example.yummyfood.Domain.CartItem;
import com.example.yummyfood.Domain.DiaChi;
import com.example.yummyfood.Domain.Food;
import com.example.yummyfood.Domain.KhachHang;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ActivityPaymentUser extends AppCompatActivity {
    private RecyclerView rvCart;
    private FoodPaymentAdapter foodPaymentAdapter;
    private DatabaseReference databaseReference;
    private List<CartItem> chiTietMonAnList = new ArrayList<>();
    private Map<String, Food> monAnMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_user);

        // Ánh xạ giao diện
        rvCart = findViewById(R.id.rvCart);
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Button btnPayingPayment = findViewById(R.id.btn_paying_payment);
        ImageView btnBack = findViewById(R.id.btn_return_payment);

        // Lấy userId từ SharedPreferences
        SharedPreferences preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String userId = preferences.getString("userId", null);

        if (userId == null) {
            Toast.makeText(this, "Không tìm thấy tài khoản đã đăng nhập!", Toast.LENGTH_SHORT).show();
            finish(); // Kết thúc nếu không có userId
            return;
        }

        // Tải dữ liệu khách hàng và địa chỉ
        loadCustomerData(userId);
        loadMonAnData();

        // Sự kiện quay lại
        btnBack.setOnClickListener(view -> finish());

        // Sự kiện khi bấm nút "Thanh toán"
        btnPayingPayment.setOnClickListener(v -> {
            saveOrderToHistory(); // Lưu đơn hàng vào lịch sử
            clearCart(); // Xóa giỏ hàng
            Intent intent = new Intent(ActivityPaymentUser.this, Activity_History_Order.class);
            startActivity(intent);
            finish();
        });

        TextView tableBookingTextView = findViewById(R.id.textView12);
        tableBookingTextView.setOnClickListener(v -> startActivity(new Intent(ActivityPaymentUser.this, Profile_User.class)));


    }

    private void loadCustomerData(String userId) {
        databaseReference.child("KhachHang").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    KhachHang khachHang = snapshot.getValue(KhachHang.class);
                    if (khachHang != null) {
                        String customerName = khachHang.getTenKhachHang();
                        String customerPhone = khachHang.getSdt();
                        loadAddressData(userId, customerName, customerPhone); // Tải địa chỉ
                    }
                } else {
                    Toast.makeText(ActivityPaymentUser.this, "Không tìm thấy dữ liệu khách hàng!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ActivityPaymentUser.this, "Lỗi khi tải dữ liệu khách hàng!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAddressData(String userId, String customerName, String customerPhone) {
        databaseReference.child("DiaChi").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String address = snapshot.child("dia_chi").getValue(String.class);
                    if (address != null) {
                        String fullAddress = customerName + " | " + customerPhone + "\n" + address;
                        TextView diachiTextView = findViewById(R.id.diachi);
                        diachiTextView.setText(fullAddress);
                    } else {
                        Toast.makeText(ActivityPaymentUser.this, "Không tìm thấy địa chỉ!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActivityPaymentUser.this, "Địa chỉ không tồn tại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ActivityPaymentUser.this, "Lỗi khi tải dữ liệu địa chỉ!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMonAnData() {
        databaseReference.child("MonAn").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String idMonAn = data.getKey();
                    String tenMonAn = data.child("tenMonAn").getValue(String.class);
                    Integer giaMonAn = data.child("donGia").getValue(Integer.class);
                    String hinhAnh = data.child("hinhAnh").getValue(String.class);

                    if (idMonAn != null && tenMonAn != null && giaMonAn != null && hinhAnh != null) {
                        monAnMap.put(idMonAn, new Food(idMonAn, tenMonAn, null, giaMonAn, hinhAnh));
                    }
                }
                loadChiTietDonHang();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ActivityPaymentUser.this, "Lỗi tải dữ liệu món ăn!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadChiTietDonHang() {
        databaseReference.child("ChiTietDonHang_MonAn").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int totalAmount = 0;

                for (DataSnapshot data : snapshot.getChildren()) {
                    Integer idChiTietDonHang = data.child("idChiTietDonHang").getValue(Integer.class);
                    Integer idMonAn = data.child("idMonAn").getValue(Integer.class);
                    Integer soLuong = data.child("soLuong").getValue(Integer.class);

                    if (idChiTietDonHang != null && idMonAn != null && soLuong != null) {
                        chiTietMonAnList.add(new CartItem(idChiTietDonHang, idMonAn, soLuong));

                        Food foodItem = monAnMap.get(idMonAn.toString());
                        if (foodItem != null) {
                            totalAmount += foodItem.getPrice() * soLuong;
                        }
                    }
                }

                TextView totalTextView = findViewById(R.id.tinhtien);
                totalTextView.setText(totalAmount + "đ");

                foodPaymentAdapter = new FoodPaymentAdapter(ActivityPaymentUser.this, chiTietMonAnList, monAnMap);
                rvCart.setAdapter(foodPaymentAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ActivityPaymentUser.this, "Lỗi tải chi tiết đơn hàng!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveOrderToHistory() {
        DatabaseReference historyRef = databaseReference.child("ChiTietDonHang").push(); // Tạo một mục mới trong lịch sử
        String orderId = historyRef.getKey(); // Lấy ID tự động của đơn hàng

        // Chuẩn bị chi tiết đơn hàng
        Map<String, Object> orderDetails = new HashMap<>();
        orderDetails.put("orderTime", getCurrentTime()); // Lưu thời gian hiện tại
        orderDetails.put("trangThai", "Đã thanh toán"); // Thêm trạng thái là đã thanh toán

        // Chuẩn bị danh sách món ăn
        Map<String, Object> items = new HashMap<>();
        for (CartItem item : chiTietMonAnList) {
            Food foodItem = monAnMap.get(String.valueOf(item.getIdMonAn()));
            if (foodItem != null) {
                Map<String, Object> foodDetails = new HashMap<>();
                foodDetails.put("tenMonAn", foodItem.getName());
                foodDetails.put("soLuong", item.getSoLuong());
                foodDetails.put("giaMonAn", foodItem.getPrice());
                items.put(foodItem.getId(), foodDetails);
            }
        }

        // Thêm danh sách món ăn vào đơn hàng
        orderDetails.put("items", items);

        // Lưu vào Firebase
        historyRef.setValue(orderDetails).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Đơn hàng đã được lưu và thanh toán!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Lỗi khi lưu đơn hàng!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void clearCart() {
        DatabaseReference cartRef = databaseReference.child("ChiTietDonHang_MonAn");
        cartRef.setValue(null).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                chiTietMonAnList.clear();
                if (foodPaymentAdapter != null) {
                    foodPaymentAdapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(ActivityPaymentUser.this, "Lỗi khi xóa giỏ hàng!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
