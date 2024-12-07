package com.heri.adminWeb.service;

import com.google.firebase.database.*;
import com.heri.adminWeb.domain.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class CategoryService {
    private final DatabaseReference databaseReference;

    public CategoryService() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference("DanhMuc");
    }

    public CompletableFuture<List<Category>> findAllAsync() {
        CompletableFuture<List<Category>> future = new CompletableFuture<>();
        List<Category> categories = new ArrayList<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idDanhMuc = snapshot.getKey();
                    String hinhAnh = snapshot.child("hinhAnh").getValue(String.class);
                    String tenDanhMuc = snapshot.child("tenDanhMuc").getValue(String.class);

                    if (hinhAnh != null && tenDanhMuc != null) {
                        Category category = new Category(idDanhMuc, tenDanhMuc, hinhAnh);
                        categories.add(category);
                    }
                }
                future.complete(categories); // Trả về danh sách khi tải xong
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(new RuntimeException("Error reading data: " + databaseError.getMessage()));
            }
        });

        return future;
    }

    public void addCategory(Category category) {
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
                Map<String, Object> categoryMap = new HashMap<>();
                categoryMap.put("tenDanhMuc", category.getTenDanhMuc());
                categoryMap.put("hinhAnh", category.getHinhAnh());

                // Lưu vào Firebase
                databaseReference.child(newKey).setValue(categoryMap, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        System.err.println("Lỗi khi thêm danh mục: " + databaseError.getMessage());
                    } else {
                        System.out.println("Thêm danh mục thành công!");
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Lỗi khi lấy danh sách danh mục: " + databaseError.getMessage());
            }
        });
    }

    //update
    public Category findByKey(String key) {
        final CompletableFuture<Category> future = new CompletableFuture<>();

        databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String tenDanhMuc = dataSnapshot.child("tenDanhMuc").getValue(String.class);
                    String hinhAnh = dataSnapshot.child("hinhAnh").getValue(String.class);

                    if (tenDanhMuc != null && hinhAnh != null) {
                        future.complete(new Category(key, tenDanhMuc, hinhAnh));
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


    public void updateCategory(String key, Category updatedCategory) {
        // Chuyển đổi Category thành Map
        Map<String, Object> categoryMap = new HashMap<>();
        categoryMap.put("tenDanhMuc", updatedCategory.getTenDanhMuc());
        categoryMap.put("hinhAnh", updatedCategory.getHinhAnh());

        // Cập nhật dữ liệu trong Firebase
        databaseReference.child(key).updateChildren(categoryMap, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.err.println("Lỗi khi cập nhật danh mục: " + databaseError.getMessage());
            } else {
                System.out.println("Cập nhật danh mục thành công!");
            }
        });
    }



    public void deleteCategory(String idDanhMuc) {
        databaseReference.child(idDanhMuc).removeValue((databaseError, databaseReference) -> {
            if (databaseError != null) {
                System.err.println("Lỗi khi xóa danh mục: " + databaseError.getMessage());
            } else {
                System.out.println("Xóa danh mục thành công!");
            }
        });
    }

}
