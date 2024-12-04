package com.example.yummyfood.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummyfood.Domain.CartItem;
import com.example.yummyfood.Domain.Food;
import com.example.yummyfood.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private final List<CartItem> chiTietMonAnList; // Danh sách chi tiết món ăn
    private final Map<String, Food> monAnMap; // Lưu thông tin món ăn (idMonAn -> đối tượng Food)
    private final Context context;
    private final List<CartItem> deleteItems = new ArrayList<>();

    public CartAdapter(Context context, List<CartItem> chiTietMonAnList, Map<String, Food> monAnMap) {
        this.context = context;
        this.chiTietMonAnList = chiTietMonAnList;
        this.monAnMap = monAnMap;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    public List<CartItem> getSelectedItems() {
        return deleteItems; // Trả về danh sách các mục được chọn
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem chiTietMonAn = chiTietMonAnList.get(position);

        // Xử lý CheckBox
        holder.checkbox.setOnCheckedChangeListener(null); // Xóa listener cũ trước khi đặt mới
        holder.checkbox.setChecked(deleteItems.contains(chiTietMonAn)); // Đặt trạng thái CheckBox

        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                deleteItems.add(chiTietMonAn);
            } else {
                deleteItems.remove(chiTietMonAn);
            }
        });

        int idMonAn = chiTietMonAn.getIdMonAn();
        int soLuong = chiTietMonAn.getSoLuong();

        // Lấy thông tin món ăn từ monAnMap
        Food monAn = monAnMap.get(String.valueOf(idMonAn));

        if (monAn != null) {
            holder.tvMonAn.setText(monAn.getName());
            holder.tvGiaMonAn.setText(monAn.getPrice()+" VND"); // Hiển thị giá
            holder.tvSoLuong.setText(String.valueOf(soLuong));

            // Sử dụng Glide để hiển thị hình ảnh
            Glide.with(context)
                    .load(monAn.getImage()) // URL hình ảnh
                    .into(holder.ivHinhAnh);
        } else {
            holder.tvMonAn.setText("Món ăn không xác định");
            holder.tvGiaMonAn.setText("0 đ");
            holder.tvSoLuong.setText(String.valueOf(soLuong));
        }

        // Xử lý khi nhấn nút tăng số lượng
        holder.btnPlus.setOnClickListener(v -> {
            chiTietMonAn.setSoLuong(chiTietMonAn.getSoLuong() + 1);
            holder.tvSoLuong.setText(String.valueOf(chiTietMonAn.getSoLuong()));
            notifyItemChanged(position); // Cập nhật lại item

            // TODO: Thêm logic cập nhật số lượng lên Firebase
            DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("ChiTietDonHang_MonAn");

            cartRef.orderByChild("idMonAn").equalTo(idMonAn)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data : snapshot.getChildren()) {
                                String cartKey = data.getKey(); // Lấy key của bản ghi
                                if (cartKey != null) {
                                    // Cập nhật số lượng
                                    cartRef.child(cartKey).child("soLuong").setValue(chiTietMonAn.getSoLuong())
                                            .addOnSuccessListener(aVoid -> Log.d("FirebaseUpdate", "Cập nhật số lượng thành công!"))
                                            .addOnFailureListener(e -> Log.e("FirebaseUpdate", "Cập nhật thất bại: " + e.getMessage()));
                                } else {
                                    Log.e("FirebaseError", "Không tìm thấy key cho idMonAn: " + idMonAn);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("FirebaseError", "Lỗi khi truy vấn Firebase: " + error.getMessage());
                        }
                    });

            Log.d("CartAdapter", "Tăng số lượng món ăn ID: " + idMonAn + " - Số lượng: " + chiTietMonAn.getSoLuong());
        });

        // Xử lý khi nhấn nút giảm số lượng
        holder.btnMinus.setOnClickListener(v -> {
            if (chiTietMonAn.getSoLuong() > 1) {
                chiTietMonAn.setSoLuong(chiTietMonAn.getSoLuong() - 1);
                holder.tvSoLuong.setText(String.valueOf(chiTietMonAn.getSoLuong()));
                notifyItemChanged(position); // Cập nhật lại item

                // TODO: Thêm logic cập nhật số lượng lên Firebase
                DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("ChiTietDonHang_MonAn");

                cartRef.orderByChild("idMonAn").equalTo(idMonAn)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot data : snapshot.getChildren()) {
                                    String cartKey = data.getKey(); // Lấy key của bản ghi
                                    if (cartKey != null) {
                                        // Cập nhật số lượng
                                        cartRef.child(cartKey).child("soLuong").setValue(chiTietMonAn.getSoLuong())
                                                .addOnSuccessListener(aVoid -> Log.d("FirebaseUpdate", "Cập nhật số lượng thành công!"))
                                                .addOnFailureListener(e -> Log.e("FirebaseUpdate", "Cập nhật thất bại: " + e.getMessage()));
                                    } else {
                                        Log.e("FirebaseError", "Không tìm thấy key cho idMonAn: " + idMonAn);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e("FirebaseError", "Lỗi khi truy vấn Firebase: " + error.getMessage());
                            }
                        });


                Log.d("CartAdapter", "Giảm số lượng món ăn ID: " + idMonAn + " - Số lượng: " + chiTietMonAn.getSoLuong());
            } else {
                Log.d("CartAdapter", "Số lượng không thể nhỏ hơn 1");
            }
        });
    }

    @Override
    public int getItemCount() {
        return chiTietMonAnList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMonAn, tvGiaMonAn, tvSoLuong;
        ImageView ivHinhAnh, btnPlus, btnMinus;
        CheckBox checkbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMonAn = itemView.findViewById(R.id.tvMonAn);
            tvGiaMonAn = itemView.findViewById(R.id.tvGiaMonAn);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            ivHinhAnh = itemView.findViewById(R.id.ivHinhAnh);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            checkbox = itemView.findViewById(R.id.checkbox);
        }
    }
}
