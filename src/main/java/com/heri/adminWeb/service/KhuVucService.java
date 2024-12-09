package com.heri.adminWeb.service;

import com.google.firebase.database.*;
import com.heri.adminWeb.domain.Category;
import com.heri.adminWeb.domain.KhuVuc;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class KhuVucService {
    private final DatabaseReference databaseReference;

    public KhuVucService() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference("KhuVuc");
    }

    public CompletableFuture<List<KhuVuc>> findAllAsync() {
        CompletableFuture<List<KhuVuc>> future = new CompletableFuture<>();
        List<KhuVuc> khuVucs = new ArrayList<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.getKey();
                    String tenKV = snapshot.child("tenKV").getValue(String.class);

                    if (tenKV != null) {
                        KhuVuc khuVuc = new KhuVuc(id, tenKV);
                        khuVucs.add(khuVuc);
                    }
                }
                future.complete(khuVucs); // Trả về danh sách khi tải xong
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(new RuntimeException("Error reading data: " + databaseError.getMessage()));
            }
        });

        return future;
    }

    //add khu vuc
    // Phương thức thêm khu vực mới
    public void addKhuVuc(KhuVuc khuVuc) {
        // Lấy tất cả các khóa hiện tại
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long maxKey = -1; // Biến để lưu khóa lớn nhất

                // Lặp qua tất cả các khóa để tìm khóa lớn nhất
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    if (key != null) {
                        try {
                            long currentKey = Long.parseLong(key);
                            if (currentKey > maxKey) {
                                maxKey = currentKey; // Cập nhật khóa lớn nhất
                            }
                        } catch (NumberFormatException e) {
                            // Bỏ qua nếu không thể chuyển đổi khóa thành số
                            System.err.println("Lỗi chuyển đổi khóa: " + e.getMessage());
                        }
                    }
                }

                // Tạo khóa mới bằng khóa lớn nhất cộng thêm 1
                String newKey = String.valueOf(maxKey + 1);

                // Chuyển đổi Category thành Map
                Map<String, Object> khuVucMap = new HashMap<>();
                khuVucMap.put("tenKV", khuVuc.getTenKV());

                // Lưu vào Firebase
                databaseReference.child(newKey).setValue(khuVucMap, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        System.err.println("Lỗi khi thêm: " + databaseError.getMessage());
                    } else {
                        System.out.println("Thêm khu vực thành công!");
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Lỗi khi lấy danh sách: " + databaseError.getMessage());
            }
        });
    }

    public KhuVuc findByKey(String key) {
        final CompletableFuture<KhuVuc> future = new CompletableFuture<>();

        databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String tenKV = dataSnapshot.child("tenKV").getValue(String.class);

                    if (tenKV != null) {
                        future.complete(new KhuVuc(key, tenKV));
                    } else {
                        future.complete(null);
                    }
                } else {
                    future.complete(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(new RuntimeException("Lỗi: " + databaseError.getMessage()));
            }
        });

        try {
            return future.get(); // Chờ lấy dữ liệu
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateKhuVuc(String key, KhuVuc updatedKhuVuc) {
        Map<String, Object> khuVucMap = new HashMap<>();
        khuVucMap.put("tenKV", updatedKhuVuc.getTenKV());

        // Cập nhật dữ liệu trong Firebase
        databaseReference.child(key).updateChildren(khuVucMap, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.err.println("Lỗi khi cập nhật danh mục: " + databaseError.getMessage());
            } else {
                System.out.println("Cập nhật danh mục thành công!");
            }
        });
    }

    public void deleteKhuVuc(String id) {
        databaseReference.child(id).removeValue((databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.err.println("Lỗi khi xóa danh mục: " + databaseError.getMessage());
            } else {
                System.out.println("Xóa danh mục thành công!");
            }
        });
    }
}
