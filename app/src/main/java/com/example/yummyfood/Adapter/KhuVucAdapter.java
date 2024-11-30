package com.example.yummyfood.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Domain.KhuVuc;
import com.example.yummyfood.R;
import com.example.yummyfood.dsBan;

import java.util.List;

public class KhuVucAdapter extends RecyclerView.Adapter<KhuVucAdapter.KhuVucViewHolder> {

    private Context context;
    private List<KhuVuc> khuVucList;

    public KhuVucAdapter(Context context, List<KhuVuc> khuVucList) {
        this.context = context;
        this.khuVucList = khuVucList;
    }

    @Override
    public KhuVucViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_tang, parent, false);
        return new KhuVucViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(KhuVucViewHolder holder, int position) {
        KhuVuc khuVuc = khuVucList.get(position);
        holder.tenKVTextView.setText(khuVuc.getTenKV());

        // Handle click event
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, dsBan.class);
            intent.putExtra("idKhuVuc", khuVuc.getIdKhuVuc());  // Chuyển idKhuVuc là String
            intent.putExtra("tenKhuVuc", khuVuc.getTenKV());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return khuVucList.size();
    }

    public static class KhuVucViewHolder extends RecyclerView.ViewHolder {
        TextView tenKVTextView;

        public KhuVucViewHolder(View itemView) {
            super(itemView);
            tenKVTextView = itemView.findViewById(R.id.tenTangTextView);
        }
    }
}
