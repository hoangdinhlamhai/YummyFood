package com.example.yummyfood.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummyfood.Domain.CartItem;
import com.example.yummyfood.Domain.Food;
import com.example.yummyfood.R;

import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private final List<CartItem> chiTietMonAnList; // Danh sách chi tiết món ăn
    private final Map<String, Food> monAnMap; // Lưu thông tin món ăn (idMonAn -> đối tượng Food)
    private final Context context;

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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem chiTietMonAn = chiTietMonAnList.get(position);
        int idMonAn = chiTietMonAn.getIdMonAn();
        int soLuong = chiTietMonAn.getSoLuong();

        // Lấy thông tin món ăn từ monAnMap
        Food monAn = monAnMap.get(String.valueOf(idMonAn));

//        Log.d("CartDebug", "idMonAn: " + idMonAn); // In giá trị idMonAn
//        if (monAn != null) {
//            Log.d("CartDebug", "Món ăn tìm được: " + monAn.toString()); // In thông tin món ăn nếu tìm thấy
//        } else {
//            Log.d("CartDebug", "Không tìm thấy món ăn với idMonAn: " + idMonAn); // Thông báo nếu không tìm thấy
//        }

        if (monAn != null) {
            holder.tvMonAn.setText(monAn.getName());
            holder.tvGiaMonAn.setText(monAn.getPrice()+"VND"); // Hiển thị giá
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
    }

    @Override
    public int getItemCount() {
        return chiTietMonAnList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMonAn, tvGiaMonAn, tvSoLuong;
        ImageView ivHinhAnh;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMonAn = itemView.findViewById(R.id.tvMonAn);
            tvGiaMonAn = itemView.findViewById(R.id.tvGiaMonAn);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            ivHinhAnh = itemView.findViewById(R.id.ivHinhAnh);
        }
    }
}
