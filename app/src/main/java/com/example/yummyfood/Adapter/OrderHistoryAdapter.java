package com.example.yummyfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Domain.Order;
import com.example.yummyfood.Domain.OrderItem;
import com.example.yummyfood.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {
    private final Context context;
    private final List<Order> orders;

    public OrderHistoryAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
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

        // Hiển thị trạng thái bên dưới ngày đặt hàng
        String status = order.getTrangThai();
        holder.tvOrderStatus.setText("Trạng thái: " + status);

        // Gán danh sách món ăn cho RecyclerView bên trong
        ListOrderItemAdapter listOrderItemAdapter = new ListOrderItemAdapter(context, order.getItems());
        holder.rvOrderItems.setLayoutManager(new LinearLayoutManager(context));
        holder.rvOrderItems.setAdapter(listOrderItemAdapter);

        // Thay đổi nội dung và hành động của btn_status dựa trên trạng thái
        if ("Đang vận chuyển".equals(status)) {
            holder.btnStatus.setVisibility(View.VISIBLE);
            holder.btnStatus.setText("Đang vận chuyển");
            holder.btnStatus.setEnabled(false);
            holder.btnStatus.setBackgroundResource(R.drawable.button_disabled);
        } else if ("Đang chế biến".equals(status)) {
            holder.btnStatus.setVisibility(View.VISIBLE);
            holder.btnStatus.setText("Hủy đơn");
            holder.btnStatus.setEnabled(true);
            holder.btnStatus.setBackgroundResource(R.drawable.button_cancel);
            holder.btnStatus.setOnClickListener(v -> showCancelDialog(order, holder));
        } else if ("Đã giao".equals(status)) {
            holder.btnStatus.setVisibility(View.VISIBLE);
            holder.btnStatus.setText("Đánh giá");
            holder.btnStatus.setEnabled(true);
            holder.btnStatus.setBackgroundResource(R.drawable.button_rate);
            holder.btnStatus.setOnClickListener(v -> {
                // Logic chuyển đến màn hình đánh giá
                Toast.makeText(context, "Chức năng đánh giá chưa được triển khai.", Toast.LENGTH_SHORT).show();
            });
        } else if ("Đã hủy".equals(status)) {
            holder.btnStatus.setVisibility(View.VISIBLE);
            holder.btnStatus.setText("Đã hủy");
            holder.btnStatus.setEnabled(false);
            holder.btnStatus.setBackgroundResource(R.drawable.button_disabled);
        } else {
            holder.btnStatus.setVisibility(View.GONE); // Ẩn nút nếu trạng thái không rõ
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    // Hiển thị Dialog xác nhận hủy đơn hàng
    private void showCancelDialog(Order order, ViewHolder holder) {
        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle("Hủy đơn hàng")
                .setMessage("Bạn có chắc muốn hủy đơn hàng này không?")
                .setPositiveButton("Có", (dialog, which) -> cancelOrder(order, holder)) // Gọi hàm hủy đơn nếu nhấn "Có"
                .setNegativeButton("Không", (dialog, which) -> dialog.dismiss()) // Đóng dialog nếu nhấn "Không"
                .show();
    }

    // Hủy đơn hàng và cập nhật trạng thái
    private void cancelOrder(Order order, ViewHolder holder) {
        DatabaseReference orderRef = FirebaseDatabase.getInstance()
                .getReference("ChiTietDonHang")
                .child(order.getOrderId()); // Sử dụng ID đơn hàng

        orderRef.child("trangThai").setValue("Đã hủy").addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Cập nhật giao diện sau khi hủy
                holder.btnStatus.setText("Đã hủy");
                holder.btnStatus.setEnabled(false);
                holder.btnStatus.setBackgroundResource(R.drawable.button_disabled);
                holder.tvOrderStatus.setText("Trạng thái: Đã hủy");
                Toast.makeText(context, "Đơn hàng đã được hủy thành công!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Không thể hủy đơn hàng!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderTime, tvOrderStatus;
        RecyclerView rvOrderItems;
        Button btnStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderTime = itemView.findViewById(R.id.tv_order_time);
            tvOrderStatus = itemView.findViewById(R.id.tv_order_status);
            rvOrderItems = itemView.findViewById(R.id.rvOrderItems);
            btnStatus = itemView.findViewById(R.id.btn_status);
        }
    }
}
