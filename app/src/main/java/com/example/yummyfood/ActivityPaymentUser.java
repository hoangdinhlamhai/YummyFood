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
        btnReturnRetailFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityPaymentUser.this, FoodDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TextView txt1 = findViewById(R.id.textView12);
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityPaymentUser.this, edit_address.class);
                startActivity(intent);
                finish();
            }
        });

        Button btnPayingPayment = findViewById(R.id.btn_paying_payment);
        btnPayingPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        // Khởi tạo RecyclerView
        rvCart = findViewById(R.id.rvCart);
        rvCart.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Tải dữ liệu từ Firebase
        loadMonAnData();
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
                for (DataSnapshot data : snapshot.getChildren()) {
                    Integer idChiTietDonHang = data.child("idChiTietDonHang").getValue(Integer.class);
                    Integer idMonAn = data.child("idMonAn").getValue(Integer.class);
                    Integer soLuong = data.child("soLuong").getValue(Integer.class);

                    if (idChiTietDonHang != null && idMonAn != null && soLuong != null) {
                        chiTietMonAnList.add(new CartItem(idChiTietDonHang, idMonAn, soLuong));
                    }
                }

                foodPaymentAdapter = new FoodPaymentAdapter(ActivityPaymentUser.this, chiTietMonAnList, monAnMap);
                rvCart.setAdapter(foodPaymentAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ActivityPaymentUser.this, "Lỗi tải chi tiết đơn hàng!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}