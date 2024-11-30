package com.example.yummyfood;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Adapter.BanAdapter;
import com.example.yummyfood.Domain.Ban;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class dsBan extends AppCompatActivity {

    private RecyclerView rvTableList;
    private List<Ban> tableList;
    private BanAdapter banAdapter;
    private DatabaseReference databaseReference;
    private TextView tenKhuVucTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_ban);

        String tenKhuVuc = getIntent().getStringExtra("tenKhuVuc");
        String idKhuVuc = getIntent().getStringExtra("idKhuVuc");

        tenKhuVucTextView = findViewById(R.id.title);
        tenKhuVucTextView.setText(tenKhuVuc);

        rvTableList = findViewById(R.id.recyclerView);

        // Sử dụng GridLayoutManager với 2 cột
        rvTableList.setLayoutManager(new GridLayoutManager(this, 2));
        rvTableList.setHasFixedSize(true);

        tableList = new ArrayList<>();
        banAdapter = new BanAdapter(tableList);
        rvTableList.setAdapter(banAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Ban");

        loadBanData(idKhuVuc);
    }

    private void loadBanData(String idKhuVuc) {
        databaseReference.orderByChild("idKhuVuc").equalTo(Integer.parseInt(idKhuVuc)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tableList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Ban ban = snapshot.getValue(Ban.class);
                    if (ban != null) {
                        tableList.add(ban);
                    }
                }
                banAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("dsBan", "Error loading data", databaseError.toException());
            }
        });
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}