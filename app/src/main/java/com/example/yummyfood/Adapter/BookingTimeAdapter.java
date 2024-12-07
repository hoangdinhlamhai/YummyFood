package com.example.yummyfood.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Domain.ChiTietDatBan;
import com.example.yummyfood.R;

import java.util.List;

public class BookingTimeAdapter extends RecyclerView.Adapter<BookingTimeAdapter.ViewHolder> {

    private List<ChiTietDatBan> bookingList;

    public BookingTimeAdapter(List<ChiTietDatBan> bookingList) {
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giodatban, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChiTietDatBan booking = bookingList.get(position);
        holder.tvDate.setText(booking.getNgay()); // Hiển thị ngày
        holder.tvStartTime.setText(booking.getThoiGianBatDau());
        holder.tvEndTime.setText(booking.getThoiGianKetThuc());
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvStartTime, tvEndTime; // Thêm tvDate

        ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate); // Khởi tạo tvDate
            tvStartTime = itemView.findViewById(R.id.tvStartTime);
            tvEndTime = itemView.findViewById(R.id.tvEndTime);
        }
    }
}