package com.example.yummyfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Domain.Table;
import com.example.yummyfood.R;

import java.util.List;

public class TableListAdapter extends RecyclerView.Adapter<TableListAdapter.TableViewHolder> {

    private Context context;
    private List<Table> tableList;

    public TableListAdapter(Context context, List<Table> tableList) {
        this.context = context;
        this.tableList = tableList;
    }

    @Override
    public TableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_table, parent, false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TableViewHolder holder, int position) {
        Table table = tableList.get(position);

        // Gán tên bàn
        holder.tvTableName.setText(table.getTenBan());
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }

    // ViewHolder cho từng bàn
    public class TableViewHolder extends RecyclerView.ViewHolder {
        TextView tvTableName;

        public TableViewHolder(View itemView) {
            super(itemView);
            tvTableName = itemView.findViewById(R.id.table_name);
        }
    }
}
