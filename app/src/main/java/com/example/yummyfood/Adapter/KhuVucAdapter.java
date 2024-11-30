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
import com.example.yummyfood.dsBan;  // Activity hiển thị danh sách bàn

import java.util.List;

public class KhuVucAdapter extends RecyclerView.Adapter<KhuVucAdapter.KhuVucViewHolder> {

    private Context context;
    private List<KhuVuc> khuVucList;

    // Constructor
    public KhuVucAdapter(Context context, List<KhuVuc> khuVucList) {
        this.context = context;
        this.khuVucList = khuVucList;
    }

    @Override
    public KhuVucViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate layout item
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_tang, parent, false);
        return new KhuVucViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(KhuVucViewHolder holder, int position) {
        // Get KhuVuc item from the list
        KhuVuc khuVuc = khuVucList.get(position);

        // Set the name of the floor (Tầng)
        holder.tenKVTextView.setText(khuVuc.getTenKV());

        // Handle click event to open dsBan Activity when item (floor) is clicked
        holder.itemView.setOnClickListener(v -> {
            // Pass the position along with other data
            Intent intent = new Intent(context, dsBan.class);
            intent.putExtra("idKhuVuc", khuVuc.getIdKhuVuc());  // Pass floor ID
            intent.putExtra("tenKhuVuc", khuVuc.getTenKV());  // Pass floor name
            intent.putExtra("position", position);  // Pass position to know which item was clicked
            context.startActivity(intent);  // Start the activity
        });
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
            tenKVTextView = itemView.findViewById(R.id.tenTangTextView);  // Reference to the TextView in the item
        }
    }
}
