package com.example.yummyfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Domain.Order;
import com.example.yummyfood.Domain.OrderItem;
import com.example.yummyfood.R;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {
    private final Context context;
    private final List<Order> orders;

    public OrderHistoryAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders; // Danh sách các đơn hàng
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);

        // Hiển thị ngày đặt hàng
        holder.tvOrderTime.setText("Ngày đặt hàng: " + order.getOrderTime());

        // Gán danh sách món ăn cho RecyclerView bên trong
        ListOrderItemAdapter listOrderItemAdapter = new ListOrderItemAdapter(context, order.getItems());
        holder.rvOrderItems.setLayoutManager(new LinearLayoutManager(context));
        holder.rvOrderItems.setAdapter(listOrderItemAdapter);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderTime;
        RecyclerView rvOrderItems;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderTime = itemView.findViewById(R.id.tvOrderTime);
            rvOrderItems = itemView.findViewById(R.id.rvOrderItems);
        }
    }
}
