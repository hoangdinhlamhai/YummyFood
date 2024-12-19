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

    //them ban
    public void addBan(Ban ban) {
        // Đọc tất cả các key trong bảng Ban
        banRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long maxKey = -1; // Biến lưu key lớn nhất

                // Lặp qua các key để tìm giá trị lớn nhất
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    if (key != null) {
                        try {
                            long currentKey = Long.parseLong(key);
                            if (currentKey > maxKey) {
                                maxKey = currentKey; // Cập nhật key lớn nhất
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Lỗi chuyển đổi key: " + e.getMessage());
                        }
                    }
                }

                // Tạo key mới bằng key lớn nhất + 1
                String newKey = String.valueOf(maxKey + 1);

                // Chuyển đổi ban thành Map
                Map<String, Object> banMap = new HashMap<>();
                banMap.put("tenBan", ban.getTenBan());
                banMap.put("soLuongGhe", ban.getSoLuongGhe());
                banMap.put("idKhuVuc", ban.getKeyKhuVuc());

                // Thêm món ăn vào Firebase
                banRef.child(newKey).setValue(banMap, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        System.err.println("Lỗi khi thêm món ăn: " + databaseError.getMessage());
                    } else {
                        System.out.println("Thêm món ăn thành công với key: " + newKey);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Lỗi khi lấy dữ liệu: " + databaseError.getMessage());
            }
        });
    }

    //edit ban
    // Tìm ban theo ID
    public CompletableFuture<Ban> findById(String id) {
        CompletableFuture<Ban> future = new CompletableFuture<>();
        banRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String tenBan = dataSnapshot.child("tenBan").getValue(String.class);
                    Integer soLuongGhe = dataSnapshot.child("soLuongGhe").getValue(Integer.class);
                    Integer idKhuVuc = dataSnapshot.child("idKhuVuc").getValue(Integer.class);

                    // In ra để kiểm tra
                    System.out.println("Dữ liệu lấy được:" + id);
                    System.out.println("tenBan: " + tenBan);
                    System.out.println("soLuongGhe: " + soLuongGhe);
                    System.out.println("idKhuVuc: " + idKhuVuc);

                    if (tenBan != null && soLuongGhe != null && idKhuVuc != null) {
                        Ban ban = new Ban(id, tenBan, soLuongGhe, idKhuVuc);
                        System.out.println("aaaaaaaaaaa: " + ban);
                        future.complete(ban);
                    } else {
                        future.completeExceptionally(new RuntimeException("Dữ liệu không đầy đủ."));
                    }
                } else {
                    future.completeExceptionally(new RuntimeException("Không tìm thấy ban với ID: " + id));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(new RuntimeException("Error reading data: " + databaseError.getMessage()));
            }
        });
        return future;
    }

    // Cập nhật
    public void updateBan(Ban ban) {
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("tenBan", ban.getTenBan());
        updateData.put("soLuongGhe", ban.getSoLuongGhe());
        updateData.put("idKhuVuc", ban.getKeyKhuVuc());

        banRef.child(ban.getKeyBan()).updateChildren(updateData, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.err.println("Lỗi khi cập nhật: " + databaseError.getMessage());
            } else {
                System.out.println("Cập nhật thành công!");
            }
        });
    }

    // Xóa
    public void delete(String keyBan) {
        banRef.child(keyBan).removeValue((databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.err.println("Lỗi khi xóa: " + databaseError.getMessage());
            } else {
                System.out.println("Xóa danh mục thành công!");
            }
        });
    }
}
