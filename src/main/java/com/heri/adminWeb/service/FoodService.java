package com.heri.adminWeb.service;

import com.google.firebase.database.*;
import com.heri.adminWeb.domain.Food;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class FoodService {
    private final DatabaseReference monAnRef;
    private final DatabaseReference danhMucRef;

    public FoodService() {
        this.monAnRef = FirebaseDatabase.getInstance().getReference("MonAn");
        this.danhMucRef = FirebaseDatabase.getInstance().getReference("DanhMuc");
    }

    public CompletableFuture<List<Food>> findAllWithCategoryAsync() {
        CompletableFuture<List<Food>> future = new CompletableFuture<>();

        // Lấy tất cả danh mục từ Firebase
        danhMucRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot danhMucSnapshot) {
                Map<String, String> danhMucMap = new HashMap<>();

                // Lưu ánh xạ giữa idDanhMuc và tenDanhMuc
                for (DataSnapshot snapshot : danhMucSnapshot.getChildren()) {
                    String idDanhMuc = snapshot.getKey();
                    String tenDanhMuc = snapshot.child("tenDanhMuc").getValue(String.class);
                    if (idDanhMuc != null && tenDanhMuc != null) {
                        danhMucMap.put(idDanhMuc, tenDanhMuc);
                    }
                }

                // Lấy tất cả món ăn từ Firebase
                monAnRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot monAnSnapshot) {
                        List<Food> monAnList = new ArrayList<>();

                        for (DataSnapshot snapshot : monAnSnapshot.getChildren()) {
                            String idMonAn = snapshot.getKey();
                            String tenMonAn = snapshot.child("tenMonAn").getValue(String.class);
                            Integer donGia = snapshot.child("donGia").getValue(Integer.class);
                            String moTa = snapshot.child("moTa").getValue(String.class);
                            String hinhAnh = snapshot.child("hinhAnh").getValue(String.class);
                            Integer idDanhMuc = snapshot.child("idDanhMuc").getValue(Integer.class);

                            if (tenMonAn != null && donGia != null && moTa != null && hinhAnh != null && idDanhMuc != null) {
                                // Tìm tên danh mục từ idDanhMuc
                                String tenDanhMuc = danhMucMap.getOrDefault(String.valueOf(idDanhMuc), "Không xác định");
                                Food monAn = new Food(idMonAn, tenMonAn, hinhAnh, moTa, idDanhMuc, donGia, tenDanhMuc);
                                monAnList.add(monAn);
                            }
                        }
                        future.complete(monAnList); // Trả về danh sách món ăn
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        future.completeExceptionally(new RuntimeException("Error reading MonAn: " + databaseError.getMessage()));
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

    //add food
    public void addMonAn(Food monAn) {
        // Đọc tất cả các key trong bảng MonAn
        monAnRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

                // Chuyển đổi MonAn thành Map
                Map<String, Object> monAnMap = new HashMap<>();
                monAnMap.put("tenMonAn", monAn.getTenMonAn());
                monAnMap.put("donGia", monAn.getDonGia());
                monAnMap.put("moTa", monAn.getMoTa());
                monAnMap.put("hinhAnh", monAn.getHinhAnh());
                monAnMap.put("idDanhMuc", monAn.getIdDanhMuc());

                // Thêm món ăn vào Firebase
                monAnRef.child(newKey).setValue(monAnMap, (databaseError, databaseReference) -> {
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

    //edit food
    // Tìm món ăn theo ID
    public CompletableFuture<Food> findById(String id) {
        CompletableFuture<Food> future = new CompletableFuture<>();
        monAnRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String tenMonAn = dataSnapshot.child("tenMonAn").getValue(String.class);
                    String moTa = dataSnapshot.child("moTa").getValue(String.class);
                    String hinhAnh = dataSnapshot.child("hinhAnh").getValue(String.class);
                    Integer donGia = dataSnapshot.child("donGia").getValue(Integer.class);
                    Integer idDanhMuc = dataSnapshot.child("idDanhMuc").getValue(Integer.class);

                    if (tenMonAn != null && moTa != null && hinhAnh != null && donGia != null && idDanhMuc != null) {
                        Food monAn = new Food(id, tenMonAn, hinhAnh, moTa, idDanhMuc, donGia, null);
                        future.complete(monAn);
                    } else {
                        future.completeExceptionally(new RuntimeException("Dữ liệu không đầy đủ."));
                    }
                } else {
                    future.completeExceptionally(new RuntimeException("Không tìm thấy món ăn với ID: " + id));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(new RuntimeException("Error reading data: " + databaseError.getMessage()));
            }
        });
        return future;
    }

    // Cập nhật món ăn
    public void updateMonAn(Food monAn) {
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("tenMonAn", monAn.getTenMonAn());
        updateData.put("moTa", monAn.getMoTa());
        updateData.put("hinhAnh", monAn.getHinhAnh());
        updateData.put("donGia", monAn.getDonGia());
        updateData.put("idDanhMuc", monAn.getIdDanhMuc());

        monAnRef.child(monAn.getKeyMonAn()).updateChildren(updateData, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.err.println("Lỗi khi cập nhật: " + databaseError.getMessage());
            } else {
                System.out.println("Cập nhật thành công!");
            }
        });
    }

    // Xóa món ăn theo ID
    public void deleteFood(String keyMonAn) {
        monAnRef.child(keyMonAn).removeValue((databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.err.println("Lỗi khi xóa danh mục: " + databaseError.getMessage());
            } else {
                System.out.println("Xóa danh mục thành công!");
            }
        });
    }
}
