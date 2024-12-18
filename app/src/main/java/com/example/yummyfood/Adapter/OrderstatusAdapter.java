package com.example.yummyfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummyfood.Domain.OrderItem;
import com.example.yummyfood.R;

import java.util.List;

public class OrderstatusAdapter extends RecyclerView.Adapter<OrderstatusAdapter.ViewHolder> {

    private final Context context;
    private final List<OrderItem> orderItemList; // Danh sách món ăn

    public OrderstatusAdapter(Context context, List<OrderItem> orderItemList) {
        this.context = context;
        this.orderItemList = orderItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderItem item = orderItemList.get(position);

        // Hiển thị dữ liệu món ăn
        holder.tvTenMon.setText(item.getTenMonAn());
        holder.tvGiaMon.setText(item.getGiaMonAn() + " đ");
        holder.tvSoLuong.setText("Số lượng: " + item.getSoLuong());

        // Hiển thị hình ảnh bằng Glide (nếu có)
        if (item.getHinhAnh() != null && !item.getHinhAnh().isEmpty()) {
            Glide.with(context).load(item.getHinhAnh()).into(holder.ivHinhMon);
        }
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenMon, tvGiaMon, tvSoLuong;
        ImageView ivHinhMon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenMon = itemView.findViewById(R.id.foodName);
            tvGiaMon = itemView.findViewById(R.id.foodPrice);
            tvSoLuong = itemView.findViewById(R.id.foodQuantity);
            ivHinhMon = itemView.findViewById(R.id.foodImg);
        }
    }
}
