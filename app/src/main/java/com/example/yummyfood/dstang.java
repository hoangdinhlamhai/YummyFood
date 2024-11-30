package com.example.yummyfood;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummyfood.Adapter.KhuVucAdapter;
import com.example.yummyfood.Domain.KhuVuc;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class dstang extends AppCompatActivity {

    private RecyclerView recyclerView;
    private KhuVucAdapter khuVucAdapter;
    private ArrayList<KhuVuc> khuVucList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dstang);

        // Khởi tạo Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("KhuVuc");

        // Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        khuVucList = new ArrayList<>();
        khuVucAdapter = new KhuVucAdapter(this, khuVucList);
        recyclerView.setAdapter(khuVucAdapter);

        // Lắng nghe sự thay đổi dữ liệu từ Firebase
        loadKhuVucData();  // Tải lại dữ liệu từ Firebase khi ứng dụng mở lại
    }

    private void loadKhuVucData() {
        khuVucList.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    KhuVuc khuVuc = snapshot.getValue(KhuVuc.class);
                    if (khuVuc != null) {
                        khuVucList.add(khuVuc);
                    }
                }
                khuVucAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(dstang.this, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
