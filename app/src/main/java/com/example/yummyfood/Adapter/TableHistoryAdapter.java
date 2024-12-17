package com.example.yummyfood.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Domain.ChiTietDatBan;
import com.example.yummyfood.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TableHistoryAdapter extends RecyclerView.Adapter<TableHistoryAdapter.ViewHolder> {

    private List<ChiTietDatBan> bookingList;
    private OnCancelClickListener cancelClickListener;

    public interface OnCancelClickListener {
        void onCancel(ChiTietDatBan booking);
    }

    public TableHistoryAdapter(List<ChiTietDatBan> bookingList, OnCancelClickListener cancelClickListener) {
        this.bookingList = bookingList;
        this.cancelClickListener = cancelClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChiTietDatBan booking = bookingList.get(position);

        holder.tableName.setText("Tên bàn: " + booking.getTenBan());
        holder.bookingDate.setText("Ngày: " + booking.getNgay());
        holder.startTime.setText("Giờ bắt đầu: " + booking.getThoiGianBatDau());
        holder.endTime.setText("Giờ kết thúc: " + booking.getThoiGianKetThuc());
        holder.bookingStatus.setText("Trạng thái: " + booking.getTrangThai());

        // Kiểm tra nếu trạng thái đã hủy
        if ("Đã hủy".equals(booking.getTrangThai())) {
            holder.btnCancel.setText("Đã hủy");
            holder.btnCancel.setEnabled(false);
        } else if (isTimeExpired(booking.getNgay(), booking.getThoiGianBatDau())) {
            // Nếu thời gian đặt đã qua, làm mờ button
            holder.btnCancel.setEnabled(false);
            holder.btnCancel.setAlpha(0.5f);
        } else {
            // Button vẫn hoạt động
            holder.btnCancel.setEnabled(true);
            holder.btnCancel.setAlpha(1.0f);
            holder.btnCancel.setText("Hủy đặt bàn");

            holder.btnCancel.setOnClickListener(v -> {
                booking.setTrangThai("Đã hủy");
                notifyItemChanged(position); // Cập nhật lại item này
                cancelClickListener.onCancel(booking); // Callback xử lý hủy
            });
        }
    }

    // Kiểm tra nếu thời gian đặt bàn đã qua thời gian hiện tại
    private boolean isTimeExpired(String ngay, String thoiGianBatDau) {
        try {
            // Kết hợp ngày và giờ thành định dạng chuẩn
            String bookingDateTime = ngay + " " + thoiGianBatDau;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

            Date bookingTime = sdf.parse(bookingDateTime);
            Date currentTime = new Date();

            return bookingTime != null && bookingTime.before(currentTime);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tableName, bookingDate, startTime, endTime, bookingStatus;
        Button btnCancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tableName = itemView.findViewById(R.id.table_name);
            bookingDate = itemView.findViewById(R.id.booking_date);
            startTime = itemView.findViewById(R.id.start_time);
            endTime = itemView.findViewById(R.id.end_time);
            bookingStatus = itemView.findViewById(R.id.booking_status);
            btnCancel = itemView.findViewById(R.id.btn_cancel_booking);
        }
    }

}
