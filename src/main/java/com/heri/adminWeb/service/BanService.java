package com.heri.adminWeb.service;

import com.google.firebase.database.*;
import com.heri.adminWeb.domain.Ban;
import com.heri.adminWeb.domain.Food;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class BanService {
    private final DatabaseReference banRef;
    private final DatabaseReference khuVucRef;

    public BanService() {
        this.banRef = FirebaseDatabase.getInstance().getReference("Ban");
        this.khuVucRef = FirebaseDatabase.getInstance().getReference("KhuVuc");
    }

    public CompletableFuture<List<Ban>> findAllWithKhuVucAsync() {
        CompletableFuture<List<Ban>> future = new CompletableFuture<>();

        // Lấy tất cả danh mục từ Firebase
        khuVucRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot khuVucSnapshot) {
                Map<String, String> khuVucMap = new HashMap<>();

                // Lưu ánh xạ giữa idkhuvuc và tenKV
                for (DataSnapshot snapshot : khuVucSnapshot.getChildren()) {
                    String id = snapshot.getKey();
                    String tenKV = snapshot.child("tenKV").getValue(String.class);
                    if (id != null && tenKV != null) {
                        khuVucMap.put(id, tenKV);
                    }
                }

                // Lấy tất cả ban từ Firebase
                banRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot banSnapshot) {
                        List<Ban> banList = new ArrayList<>();

                        for (DataSnapshot snapshot : banSnapshot.getChildren()) {
                            String keyBan = snapshot.getKey();
                            String tenBan = snapshot.child("tenBan").getValue(String.class);
                            Integer soLuongGhe = snapshot.child("soLuongGhe").getValue(Integer.class);
                            Integer keyKhuVuc = snapshot.child("idKhuVuc").getValue(Integer.class);

                            if (tenBan != null && keyBan != null && keyKhuVuc != null && soLuongGhe != null) {
                                String tenKV = khuVucMap.getOrDefault(String.valueOf(keyKhuVuc), "Không xác định");
                                Ban ban = new Ban(keyBan, tenBan, keyKhuVuc, soLuongGhe);
                                banList.add(ban);
                            }
                        }
                        future.complete(banList); // Trả về danh sách bàn
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        future.completeExceptionally(new RuntimeException("Error reading Ban: " + databaseError.getMessage()));
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(new RuntimeException("Error reading DanhMuc: " + databaseError.getMessage()));
            }
        });

        return future;
    }
}
