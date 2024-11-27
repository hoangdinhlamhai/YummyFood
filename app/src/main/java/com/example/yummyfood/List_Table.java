package com.example.yummyfood;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Adapter.ListFloorAdapter;
import com.example.yummyfood.Domain.Floor;
import com.example.yummyfood.Domain.Table;

import java.util.ArrayList;
import java.util.List;

public class List_Table extends AppCompatActivity {

    private RecyclerView rvFloorList;
    private ListFloorAdapter floorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_table);

        rvFloorList = findViewById(R.id.rvFloorList);
        rvFloorList.setLayoutManager(new LinearLayoutManager(this));

        // Tạo dữ liệu mẫu cho các tầng và bàn
        List<Floor> floorList = new ArrayList<>();
        floorList.add(new Floor("Tầng 1", createTableList("Bàn 1", "Bàn 2")));
        floorList.add(new Floor("Tầng 2", createTableList("Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4")));
        floorList.add(new Floor("Tầng Thượng", createTableList("Bàn 1", "Bàn 2")));

        // Cài đặt Adapter
        floorAdapter = new ListFloorAdapter(this, floorList);
        rvFloorList.setAdapter(floorAdapter);
    }

    // Tạo danh sách các bàn cho từng tầng
    private List<Table> createTableList(String... tableNames) {
        List<Table> tableList = new ArrayList<>();
        for (String tableName : tableNames) {
            tableList.add(new Table(tableName));
        }
        return tableList;
    }
}
