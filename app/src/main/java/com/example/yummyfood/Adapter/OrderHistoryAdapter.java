package com.example.yummyfood.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Activity_Review_User;
import com.example.yummyfood.Domain.Order;
import com.example.yummyfood.Domain.OrderItem;
import com.example.yummyfood.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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

        // Hiển thị thông tin đơn hàng
        holder.tvOrderTime.setText("Ngày đặt hàng: " + order.getOrderTime());
        holder.tvOrderStatus.setText("Trạng thái: " + order.getTrangThai());

        // Gán danh sách món ăn vào RecyclerView con
        ListOrderItemAdapter listOrderItemAdapter = new ListOrderItemAdapter(context, order.getItems());
        holder.rvOrderItems.setLayoutManager(new LinearLayoutManager(context));
        holder.rvOrderItems.setAdapter(listOrderItemAdapter);

        // Xử lý trạng thái nút dựa trên trạng thái đơn hàng
        String status = order.getTrangThai();
        if ("Đang chế biến".equals(status)) {
            // Hiển thị nút "Hủy đơn"
            holder.btnStatus.setVisibility(View.VISIBLE);
            holder.btnStatus.setText("Hủy đơn");
            holder.btnStatus.setEnabled(true);
            holder.btnStatus.setBackgroundResource(R.drawable.button_cancel);

            // Xử lý sự kiện khi bấm vào "Hủy đơn"
            holder.btnStatus.setOnClickListener(v -> showCancelDialog(order, holder));
        } else if ("Đã giao".equals(status)) {
            // Kiểm tra trạng thái đánh giá
            checkIfReviewed(order, holder);
        } else if ("Đã hủy".equals(status)) {
            holder.btnStatus.setVisibility(View.VISIBLE);
            holder.btnStatus.setText("Đã hủy");
            holder.btnStatus.setEnabled(false);
            holder.btnStatus.setBackgroundResource(R.drawable.button_disabled);
        } else {
            holder.btnStatus.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    // Hiển thị hộp thoại xác nhận hủy đơn hàng
    private void showCancelDialog(Order order, ViewHolder holder) {
        new AlertDialog.Builder(context)
                .setTitle("Xác nhận hủy đơn hàng")
                .setMessage("Bạn có chắc chắn muốn hủy đơn hàng này không?")
                .setPositiveButton("Có", (dialog, which) -> cancelOrder(order, holder))
                .setNegativeButton("Không", null)
                .show();
    }

    // Xử lý hủy đơn hàng
    private void cancelOrder(Order order, ViewHolder holder) {
        DatabaseReference orderRef = FirebaseDatabase.getInstance()
                .getReference("ChiTietDonHang")
                .child(order.getOrderId());

        // Cập nhật trạng thái đơn hàng thành "Đã hủy"
        orderRef.child("trangThai").setValue("Đã hủy").addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Cập nhật giao diện
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

    // Kiểm tra trạng thái đã đánh giá
    private void checkIfReviewed(Order order, ViewHolder holder) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("ChiTietDonHang")
                .child(order.getOrderId());

        ref.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean isReviewed = snapshot.child("isReviewed").getValue(Boolean.class) != null &&
                        snapshot.child("isReviewed").getValue(Boolean.class);

                if (isReviewed) {
                    // Nếu đã đánh giá
                    holder.btnStatus.setVisibility(View.VISIBLE);
                    holder.btnStatus.setText("Đã đánh giá");
                    holder.btnStatus.setEnabled(false);
                    holder.btnStatus.setBackgroundResource(R.drawable.button_disabled);
                } else {
                    // Nếu chưa đánh giá
                    holder.btnStatus.setVisibility(View.VISIBLE);
                    holder.btnStatus.setText("Đánh giá");
                    holder.btnStatus.setBackgroundResource(R.drawable.button_rate);
                    holder.btnStatus.setEnabled(true);

                    holder.btnStatus.setOnClickListener(v -> handleReviewClick(order, holder));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Lỗi kiểm tra trạng thái đánh giá", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Xử lý sự kiện nhấn nút "Đánh giá"
    private void handleReviewClick(Order order, ViewHolder holder) {
        SharedPreferences preferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String tenTaiKhoan = preferences.getString("tenTaiKhoan", "").trim();

        if (tenTaiKhoan.isEmpty()) {
            Toast.makeText(context, "Tên tài khoản không hợp lệ. Vui lòng đăng nhập lại!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy ID tài khoản từ Firebase
        getTaiKhoanId(tenTaiKhoan, new Callback() {
            @Override
            public void onSuccess(String idTaiKhoan) {
                // Chuyển đến màn hình đánh giá
                Intent intent = new Intent(context, Activity_Review_User.class);
                intent.putExtra("orderId", order.getOrderId());
                intent.putExtra("accountId", idTaiKhoan);
                context.startActivity(intent);

                // Cập nhật trạng thái đã đánh giá
                FirebaseDatabase.getInstance()
                        .getReference("ChiTietDonHang")
                        .child(order.getOrderId())
                        .child("isReviewed")
                        .setValue(true);

                // Cập nhật giao diện
                holder.btnStatus.setText("Đã đánh giá");
                holder.btnStatus.setEnabled(false);
                holder.btnStatus.setBackgroundResource(R.drawable.button_disabled);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Lấy ID tài khoản từ Firebase
    private void getTaiKhoanId(String tenTaiKhoan, Callback callback) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("TaiKhoan");
        ref.orderByChild("tenTaiKhoan").equalTo(tenTaiKhoan)
                .addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot data : snapshot.getChildren()) {
                                String idTaiKhoan = data.getKey();
                                callback.onSuccess(idTaiKhoan);
                                return;
                            }
                        }
                        callback.onFailure("Không tìm thấy tài khoản.");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onFailure("Lỗi: " + error.getMessage());
                    }
                });
    }

    // Interface Callback
    public interface Callback {
        void onSuccess(String result);
        void onFailure(String message);
    }

    // ViewHolder
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
