package com.example.yummyfood;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Adapter.DatabaseHelper;
import com.example.yummyfood.Adapter.TableListAdapter;

import com.example.yummyfood.Domain.Table;

import java.util.ArrayList;
import java.util.List;

public class List_Table extends AppCompatActivity {

    private RecyclerView rvTableList;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private TableListAdapter tableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_table);

        // Xử lý nút quay lại
        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(view -> finish());

        // Khởi tạo RecyclerView
        rvTableList = findViewById(R.id.rvFloorList);  // RecyclerView này sẽ hiển thị danh sách các bàn
        rvTableList.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo DatabaseHelper và database
        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();

        // Lấy danh sách các bàn từ cơ sở dữ liệu
        List<Table> tableList = loadTables();

        // Cài đặt Adapter cho RecyclerView
        tableAdapter = new TableListAdapter(this, tableList);
        rvTableList.setAdapter(tableAdapter);
    }

    // Lấy danh sách các bàn từ database
    private List<Table> loadTables() {
        List<Table> tableList = new ArrayList<>();
        Cursor cursor = database.query("Ban", new String[]{"idBan", "tenBan"}, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int tableId = cursor.getInt(cursor.getColumnIndex("idBan"));
                String tableName = cursor.getString(cursor.getColumnIndex("tenBan"));
                tableList.add(new Table(tableId, tableName));
            }
            cursor.close();
        } else {
            Log.e("List_Table", "No tables found in database.");
        }

        return tableList;
    }
}
