package com.example.yummyfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Domain.OrderItem;
import com.example.yummyfood.R;

import java.util.List;

public class ListOrderItemAdapter extends RecyclerView.Adapter<ListOrderItemAdapter.ViewHolder> {
    private final Context context;
    private final List<OrderItem> orderItems;

    public ListOrderItemAdapter(Context context, List<OrderItem> orderItems) {
        this.context = context;
        this.orderItems = orderItems; // Danh sách món ăn
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderItem item = orderItems.get(position);

        // Hiển thị thông tin món ăn
        holder.tvFoodName.setText(item.getTenMonAn());
        holder.tvFoodPrice.setText(item.getGiaMonAn() + " đ");
        holder.tvFoodQuantity.setText("Số lượng: " + item.getSoLuong());
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName, tvFoodPrice, tvFoodQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.foodName);
            tvFoodPrice = itemView.findViewById(R.id.foodPrice);
            tvFoodQuantity = itemView.findViewById(R.id.foodQuantity);
        }
    }
}
