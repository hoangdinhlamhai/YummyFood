package com.example.yummyfood.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Domain.Ban;
import com.example.yummyfood.R;

import java.util.List;

public class BanAdapter extends RecyclerView.Adapter<BanAdapter.BanViewHolder> {

    private List<Ban> banList;

    public BanAdapter(List<Ban> banList) {
        this.banList = banList;
    }

    @Override
    public BanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table, parent, false);
        return new BanViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BanViewHolder holder, int position) {
        Ban ban = banList.get(position);
        holder.tableName.setText(ban.getTenBan());
    }

    @Override
    public int getItemCount() {
        return banList.size();
    }

    public static class BanViewHolder extends RecyclerView.ViewHolder {
        TextView tableName;

        public BanViewHolder(View itemView) {
            super(itemView);
            tableName = itemView.findViewById(R.id.table_name);
        }
    }
}