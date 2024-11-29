package com.example.yummyfood.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Domain.KhuVuc;
import com.example.yummyfood.R;

import java.util.List;

public class KhuVucAdapter extends RecyclerView.Adapter<KhuVucAdapter.KhuVucViewHolder> {

    private List<KhuVuc> khuVucList;

    // Constructor
    public KhuVucAdapter(List<KhuVuc> khuVucList) {
        this.khuVucList = khuVucList;
    }

    @Override
    public KhuVucViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate layout item
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tang, parent, false);
        return new KhuVucViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(KhuVucViewHolder holder, int position) {
        // Get KhuVuc item from the list
        KhuVuc khuVuc = khuVucList.get(position);

        // Set the name of the floor (Tầng)
        holder.tenKVTextView.setText(khuVuc.getTenKV());
    }

    @Override
    public int getItemCount() {
        return khuVucList.size();
    }

    // ViewHolder
    public static class KhuVucViewHolder extends RecyclerView.ViewHolder {
        TextView tenKVTextView;

        public KhuVucViewHolder(View itemView) {
            super(itemView);
            tenKVTextView = itemView.findViewById(R.id.tenTangTextView);
        }
    }
}
