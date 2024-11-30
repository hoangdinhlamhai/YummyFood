package com.example.yummyfood.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.R;
import com.example.yummyfood.dsBan;

import java.util.ArrayList;

public class KhuVucAdapter extends RecyclerView.Adapter<KhuVucAdapter.KhuVucViewHolder> {

    private Context context;
    private ArrayList<String> khuVucList;
    private ArrayList<String> khuVucIdList;

    public KhuVucAdapter(Context context, ArrayList<String> khuVucList, ArrayList<String> khuVucIdList) {
        this.context = context;
        this.khuVucList = khuVucList;
        this.khuVucIdList = khuVucIdList;
    }

    @NonNull
    @Override
    public KhuVucViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tang, parent, false);
        return new KhuVucViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhuVucViewHolder holder, int position) {
        String tenKhuVuc = khuVucList.get(position);
        String khuVucId = khuVucIdList.get(position);

        holder.tvKhuVuc.setText(tenKhuVuc);

        // Thiết lập sự kiện click cho item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, dsBan.class);
            intent.putExtra("idKhuVuc", khuVucId); // ID khu vực
            intent.putExtra("tenKhuVuc", tenKhuVuc); // Tên khu vực
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return khuVucList.size();
    }

    public static class KhuVucViewHolder extends RecyclerView.ViewHolder {
        TextView tvKhuVuc;

        public KhuVucViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKhuVuc = itemView.findViewById(R.id.tenTangTextView);
        }
    }
}