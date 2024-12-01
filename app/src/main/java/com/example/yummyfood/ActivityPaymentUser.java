package com.example.yummyfood;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Adapter.CartAdapter;
import com.example.yummyfood.Adapter.FoodPaymentAdapter;
import com.example.yummyfood.Domain.CartItem;
import com.example.yummyfood.Domain.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityPaymentUser extends AppCompatActivity {
    private Dialog dialog;
    private RecyclerView rvCart;
    private FoodPaymentAdapter foodPaymentAdapter;
    private DatabaseReference databaseReference;
    private List<CartItem> chiTietMonAnList = new ArrayList<>();
    private Map<String, Food> monAnMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_user);

        Button btnReturnRetailFood = findViewById(R.id.btn_paying_payment);

        // Thiết lập sự kiện nhấn nút
        btnReturnRetailFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển về HomepageUserActivity
                Intent intent = new Intent(ActivityPaymentUser.this, FoodDetailActivity.class);
                startActivity(intent);
                finish(); // Kết thúc FoodRetailActivity để không trở lại được bằng nút quay lại
            }
        });
        TextView txt1 = findViewById(R.id.textView12);

// Thiết lập sự kiện nhấn vào TextView
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang edit_address khi TextView được nhấn
                Intent intent = new Intent(ActivityPaymentUser.this, edit_address.class);
                startActivity(intent);
                finish(); // Kết thúc ActivityPaymentUser để không quay lại bằng nút back
            }
        });
        // Khởi tạo nút btn_paying_payment
        Button btnPayingPayment = findViewById(R.id.btn_paying_payment);

        // Thiết lập sự kiện nhấn nút thanh toán
        btnPayingPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến ActivityOrderStatus
                Intent intent = new Intent(ActivityPaymentUser.this, scan_qr.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView btnBack = findViewById(R.id.btn_return_payment);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ///////////////////////////////////danh sach món o phần thanh toán//////////////////////

        // Initialize RecyclerView
        rvCart = findViewById(R.id.rvCart);
        rvCart.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Load data from Firebase
        loadMonAnData();

        //////////////////////////////////////////////////////////////////////////////////////////

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
                Toast.makeText(ActivityPaymentUser.this, "Lỗi tải dữ liệu món ăn!", Toast.LENGTH_SHORT).show();
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
                foodPaymentAdapter = new FoodPaymentAdapter(ActivityPaymentUser.this, chiTietMonAnList, monAnMap);
                rvCart.setAdapter(foodPaymentAdapter);
                //monAnMap đã có được các object Food
//                for (Map.Entry<String, Food> entry : monAnMap.entrySet()) {
//                    Log.d("MonAnMap", "Key: " + entry.getKey() + ", Value: " + entry.getValue().toString());
//                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ActivityPaymentUser.this, "Lỗi tải chi tiết đơn hàng!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
