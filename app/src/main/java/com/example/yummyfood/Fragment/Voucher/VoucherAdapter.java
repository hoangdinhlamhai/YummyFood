package com.example.yummyfood.Fragment.Voucher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.R;

import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {
    private Context context;
    private List<Voucher> voucherList;


    public VoucherAdapter(Context context, List<Voucher> voucherList) {
        this.context = context;
        this.voucherList = voucherList;
    }


    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_voucher_item, parent, false);
        return new VoucherViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
        Voucher voucher = voucherList.get(position);
        holder.voucherTitle.setText(voucher.getTitle());
        holder.voucherCondition.setText(voucher.getCondition());


        holder.saveButton.setOnClickListener(v -> {

            System.out.println("Đã lưu voucher: " + voucher.getTitle());
        });
    }


    @Override
    public int getItemCount() {
        return voucherList.size();
    }


    public static class VoucherViewHolder extends RecyclerView.ViewHolder {
        TextView voucherTitle, voucherCondition;
        Button saveButton;
        ImageView voucherImage;

        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            voucherTitle = itemView.findViewById(R.id.voucherTitle);
            voucherCondition = itemView.findViewById(R.id.voucherCondition);
            saveButton = itemView.findViewById(R.id.luu);
            voucherImage = itemView.findViewById(R.id.voucherImage);
        }
    }
}
