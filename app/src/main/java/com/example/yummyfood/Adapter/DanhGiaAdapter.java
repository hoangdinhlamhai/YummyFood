package com.example.yummyfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Domain.DanhGia;
import com.example.yummyfood.R;

import java.util.List;

public class DanhGiaAdapter extends RecyclerView.Adapter<DanhGiaAdapter.DanhGiaViewHolder> {
    private List<DanhGia> danhGiaList;
    private Context context;

    public DanhGiaAdapter(Context context, List<DanhGia> danhGiaList) {
        this.context = context;
        this.danhGiaList = danhGiaList;
    }

    @NonNull
    @Override
    public DanhGiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_danhgia, parent, false);
        return new DanhGiaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhGiaViewHolder holder, int position) {
        DanhGia danhGia = danhGiaList.get(position);
        holder.tvTenKhachHang.setText(danhGia.getTenKhachHang());
        holder.tvDanhGia.setText(danhGia.getDanhGia());
    }

    @Override
    public int getItemCount() {
        return danhGiaList.size();
    }

    public class DanhGiaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenKhachHang, tvDanhGia;

        public DanhGiaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenKhachHang = itemView.findViewById(R.id.tvTenKhachHang);
            tvDanhGia = itemView.findViewById(R.id.tvDanhGia);
        }
    }
}

