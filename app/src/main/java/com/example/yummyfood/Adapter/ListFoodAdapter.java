package com.example.yummyfood.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummyfood.Domain.CartItem;
import com.example.yummyfood.Domain.Food;
import com.example.yummyfood.FoodDetailActivity;
import com.example.yummyfood.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class  ListFoodAdapter extends RecyclerView.Adapter<ListFoodAdapter.FoodViewHolder> {

    private Context context;
    private List<Food> foodList;
    private DatabaseReference databaseReference;

    public ListFoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;

        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("ChiTietDonHang_MonAn");
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.tvFoodName.setText(food.getName());
        holder.tvFoodPrice.setText(food.getPrice() + "đ");

        // Load hình ảnh
        Glide.with(context).load(food.getImage()).into(holder.ivFoodImage);

        // Thêm sự kiện click
        holder.itemView.setOnClickListener(v -> {
            // Tạo Intent để chuyển sang Activity chi tiết món ăn
            Intent intent = new Intent(context, FoodDetailActivity.class);

            // Truyền idMonAn qua Intent
            intent.putExtra("idMonAn", food.getId());

            // Khởi động Activity
            context.startActivity(intent);
        });

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idDonHang = 1; // ID của đơn hàng hiện tại (tùy chỉnh theo logic của bạn)
                int idMonAn = Integer.parseInt(food.getId()); // ID món ăn

                // Lấy danh sách các item trong đơn hàng từ Firebase
                databaseReference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean isItemExists = false;
                        String existingKey = null;

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String key = snapshot.getKey();
                            if (key != null && key.startsWith(idDonHang + "_")) {
                                int existingIdMonAn = snapshot.child("idMonAn").getValue(Integer.class);
                                if (existingIdMonAn == idMonAn) {
                                    isItemExists = true;
                                    existingKey = key;
                                    break;
                                }
                            }
                        }

                        if (isItemExists) {
                            // Nếu món ăn đã tồn tại, tăng số lượng lên 1
                            String finalExistingKey = existingKey;
                            databaseReference.child(existingKey).child("soLuong").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot quantitySnapshot) {
                                    Integer currentQuantity = quantitySnapshot.getValue(Integer.class);
                                    if (currentQuantity != null) {
                                        databaseReference.child(finalExistingKey).child("soLuong").setValue(currentQuantity + 1)
                                                .addOnSuccessListener(aVoid -> {
                                                    Toast.makeText(context, "Tăng số lượng món ăn!", Toast.LENGTH_SHORT).show();
                                                })
                                                .addOnFailureListener(e -> {
                                                    Toast.makeText(context, "Cập nhật thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(context, "Không thể đọc dữ liệu: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            // Nếu món ăn chưa tồn tại, tạo key mới
                            int maxSuffix = 0;

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String key = snapshot.getKey();
                                if (key != null && key.startsWith(idDonHang + "_")) {
                                    try {
                                        int suffix = Integer.parseInt(key.split("_")[1]);
                                        if (suffix > maxSuffix) {
                                            maxSuffix = suffix;
                                        }
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            String newKey = idDonHang + "_" + (maxSuffix + 1);
                            CartItem cartItem = new CartItem(idDonHang, idMonAn, 1);

                            databaseReference.child(newKey).setValue(cartItem)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(context, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(context, "Thêm thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(context, "Không thể đọc dữ liệu: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        public View addToCart;
        TextView tvFoodName, tvFoodPrice;
        ImageView ivFoodImage;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.foodName);
            tvFoodPrice = itemView.findViewById(R.id.foodPrice);
            ivFoodImage = itemView.findViewById(R.id.foodImg);
            addToCart = itemView.findViewById(R.id.addToCart);
        }
    }
}
