package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Adapter.KhuVucAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class dstang extends AppCompatActivity {

    private RecyclerView recyclerView;
    private KhuVucAdapter khuVucAdapter;
    private ArrayList<String> khuVucList;
    private ArrayList<String> khuVucIdList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dstang);

        databaseReference = FirebaseDatabase.getInstance().getReference("KhuVuc");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        khuVucList = new ArrayList<>();
        khuVucIdList = new ArrayList<>();

        khuVucAdapter = new KhuVucAdapter(this, khuVucList, khuVucIdList);
        recyclerView.setAdapter(khuVucAdapter);

        fetchDataFromFirebase();
    }

    private void fetchDataFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                khuVucList.clear();
                khuVucIdList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        String id = snapshot.getKey();
                        String tenKV = snapshot.child("tenKV").getValue(String.class);

                        khuVucList.add(tenKV);
                        khuVucIdList.add(id);
                    } catch (Exception e) {
                        Log.e("KhuVucActivity", "Error parsing data", e);
                    }
                }
                khuVucAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(dstang.this, "Lỗi khi tải dữ liệu: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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