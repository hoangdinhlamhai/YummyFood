package com.example.yummyfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Domain.Floor;
import com.example.yummyfood.Domain.Table;
import com.example.yummyfood.R;

import java.util.List;

public class ListFloorAdapter extends RecyclerView.Adapter<ListFloorAdapter.FloorViewHolder> {

    private Context context;
    private List<Floor> floorList;

    public ListFloorAdapter(Context context, List<Floor> floorList) {
        this.context = context;
        this.floorList = floorList;
    }

    @Override
    public FloorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_floor, parent, false);
        return new FloorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FloorViewHolder holder, int position) {
        Floor floor = floorList.get(position);

        // Gán tên tầng
        holder.tvFloorName.setText(floor.getFloorName());

        // Cập nhật RecyclerView con cho các bàn trong tầng
        TableListAdapter tableAdapter = new TableListAdapter(context, floor.getTableList());
        holder.rvTables.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rvTables.setAdapter(tableAdapter);
    }

    @Override
    public int getItemCount() {
        return floorList.size();
    }

    // ViewHolder để lưu trữ các thành phần trong layout tầng
    public class FloorViewHolder extends RecyclerView.ViewHolder {
        TextView tvFloorName;
        RecyclerView rvTables;

        public FloorViewHolder(View itemView) {
            super(itemView);
            tvFloorName = itemView.findViewById(R.id.tvKhuVuc);
            rvTables = itemView.findViewById(R.id.rvTables);
        }
    }
}
