package com.heri.adminWeb.service;

import com.google.firebase.database.*;
import com.heri.adminWeb.domain.KhachHang;
import com.heri.adminWeb.domain.TaiKhoan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class KhachHangService {
    private final DatabaseReference khachHangRef;
    private final DatabaseReference taiKhoanRef;

    public KhachHangService() {
        this.khachHangRef = FirebaseDatabase.getInstance().getReference("KhachHang");
        this.taiKhoanRef = FirebaseDatabase.getInstance().getReference("TaiKhoan");
    }

    public CompletableFuture<List<KhachHang>> getAllTaiKhoanWithKhachHang() {
        CompletableFuture<List<KhachHang>> future = new CompletableFuture<>();
        List<KhachHang> resultList = new ArrayList<>();

        taiKhoanRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot taiKhoanSnapshot) {
                if (taiKhoanSnapshot.exists()) {
                    List<CompletableFuture<Void>> futures = new ArrayList<>();

                    for (DataSnapshot tkSnapshot : taiKhoanSnapshot.getChildren()) {
                        String keyTaiKhoan = tkSnapshot.getKey();
                        String tenTaiKhoan = tkSnapshot.child("tenTaiKhoan").getValue(String.class);
                        String matKhau = tkSnapshot.child("matKhau").getValue(String.class);

                        // Tìm thông tin KhachHang tương ứng
                        CompletableFuture<Void> khachHangFuture = new CompletableFuture<>();
                        khachHangRef.orderByChild("idTaiKhoan").equalTo(keyTaiKhoan)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot khachHangSnapshot) {
                                        if (khachHangSnapshot.exists()) {
                                            for (DataSnapshot khSnapshot : khachHangSnapshot.getChildren()) {
                                                String tenKhachHang = khSnapshot.child("tenKhachHang").getValue(String.class);
                                                String email = khSnapshot.child("email").getValue(String.class);
                                                String sdt = khSnapshot.child("sdt").getValue(String.class);

                                                KhachHang taiKhoanWithKhachHang = new KhachHang(
                                                        tenKhachHang, email, sdt, keyTaiKhoan, tenTaiKhoan, matKhau
                                                );

                                                resultList.add(taiKhoanWithKhachHang);
                                            }
                                        } else {
                                            // Trường hợp không có KhachHang tương ứng
                                            KhachHang taiKhoanWithKhachHang = new KhachHang(
                                                    "Người dùng chưa cập nhật", "Người dùng chưa cập nhật", "Người dùng chưa cập nhật", keyTaiKhoan, tenTaiKhoan, matKhau
                                            );

                                            resultList.add(taiKhoanWithKhachHang);
                                        }
                                        khachHangFuture.complete(null);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        khachHangFuture.completeExceptionally(new RuntimeException("Error reading KhachHang: " + databaseError.getMessage()));
                                    }
                                });

                        futures.add(khachHangFuture);
                    }

                    // Chờ tất cả truy vấn hoàn thành
                    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                            .thenRun(() -> future.complete(resultList));
                } else {
                    future.completeExceptionally(new RuntimeException("No TaiKhoan records found"));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(new RuntimeException("Error reading TaiKhoan: " + databaseError.getMessage()));
            }
        });

        return future;
    }

    // Thêm mới KhachHang với tài khoản
    public CompletableFuture<String> addKhachHangWithTaiKhoan(KhachHang khachHang) {
        CompletableFuture<String> future = new CompletableFuture<>();

        // Tạo key mới cho KhachHang và TaiKhoan
        String keyTaiKhoan = taiKhoanRef.push().getKey();
        if (keyTaiKhoan == null) {
            future.completeExceptionally(new RuntimeException("Không thể tạo key mới cho TaiKhoan"));
            return future;
        }

        // Gán key mới vào trường keyTaiKhoan
        khachHang.setKeyTaiKhoan(keyTaiKhoan);

        // Chuẩn bị dữ liệu cho KhachHang
        Map<String, Object> khachHangData = new HashMap<>();
        khachHangData.put("tenKhachHang", khachHang.getTenKhachHang());
        khachHangData.put("email", khachHang.getEmail());
        khachHangData.put("sdt", khachHang.getSdt());
        khachHangData.put("idTaiKhoan", keyTaiKhoan);

        // Chuẩn bị dữ liệu cho TaiKhoan
        Map<String, Object> taiKhoanData = new HashMap<>();
        taiKhoanData.put("tenTaiKhoan", khachHang.getTenTaiKhoan());
        taiKhoanData.put("matKhau", khachHang.getMatKhau());

        // Multi-path update để thêm vào cả hai bảng
        Map<String, Object> updates = new HashMap<>();
        updates.put("/KhachHang/" + keyTaiKhoan, khachHangData);
        updates.put("/TaiKhoan/" + keyTaiKhoan, taiKhoanData);

        // Thực hiện cập nhật
        FirebaseDatabase.getInstance().getReference().updateChildren(updates, (error, ref) -> {
            if (error != null) {
                future.completeExceptionally(new RuntimeException("Lỗi khi thêm dữ liệu: " + error.getMessage()));
            } else {
                future.complete("Thêm KhachHang và TaiKhoan thành công!");
            }
        });

        return future;
    }

    public CompletableFuture<KhachHang> findKhachHangById(String keyTaiKhoan) {
        CompletableFuture<KhachHang> future = new CompletableFuture<>();

        // Truy vấn dữ liệu từ bảng KhachHang dựa trên keyTaiKhoan
        khachHangRef.child(keyTaiKhoan).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Lấy thông tin khách hàng từ Firebase
                    String tenKhachHang = dataSnapshot.child("tenKhachHang").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String sdt = dataSnapshot.child("sdt").getValue(String.class);

                    // Lấy thông tin tài khoản từ bảng TaiKhoan
                    taiKhoanRef.child(keyTaiKhoan).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot taiKhoanSnapshot) {
                            if (taiKhoanSnapshot.exists()) {
                                String tenTaiKhoan = taiKhoanSnapshot.child("tenTaiKhoan").getValue(String.class);
                                String matKhau = taiKhoanSnapshot.child("matKhau").getValue(String.class);

                                // Tạo đối tượng KhachHang
                                KhachHang khachHang = new KhachHang(tenKhachHang, email, sdt, keyTaiKhoan, tenTaiKhoan, matKhau);
                                future.complete(khachHang);
                            } else {
                                future.completeExceptionally(new RuntimeException("Không tìm thấy thông tin tài khoản."));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            future.completeExceptionally(new RuntimeException("Lỗi đọc dữ liệu tài khoản: " + databaseError.getMessage()));
                        }
                    });
                } else {
                    future.completeExceptionally(new RuntimeException("Không tìm thấy thông tin khách hàng."));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(new RuntimeException("Lỗi đọc dữ liệu khách hàng: " + databaseError.getMessage()));
            }
        });

        return future;
    }


    public CompletableFuture<Void> updateKhachHangWithTaiKhoan(KhachHang khachHang) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        // Dữ liệu cần cập nhật
        Map<String, Object> khachHangData = new HashMap<>();
        khachHangData.put("tenKhachHang", khachHang.getTenKhachHang());
        khachHangData.put("email", khachHang.getEmail());
        khachHangData.put("sdt", khachHang.getSdt());
        khachHangData.put("idTaiKhoan", khachHang.getKeyTaiKhoan());

        Map<String, Object> taiKhoanData = new HashMap<>();
        taiKhoanData.put("tenTaiKhoan", khachHang.getTenTaiKhoan());
        taiKhoanData.put("matKhau", khachHang.getMatKhau());

        // Multi-path update
        Map<String, Object> updates = new HashMap<>();
        updates.put("/KhachHang/" + khachHang.getKeyTaiKhoan(), khachHangData);
        updates.put("/TaiKhoan/" + khachHang.getKeyTaiKhoan(), taiKhoanData);

        // Cập nhật Firebase
        FirebaseDatabase.getInstance().getReference().updateChildren(updates, (error, ref) -> {
            if (error != null) {
                future.completeExceptionally(new RuntimeException("Lỗi khi cập nhật: " + error.getMessage()));
            } else {
                future.complete(null);
            }
        });

        return future;
    }

    public CompletableFuture<String> deleteKhachHang(String keyTaiKhoan) {
        CompletableFuture<String> future = new CompletableFuture<>();

        // Xoá Khách Hàng từ bảng "KhachHang"
        khachHangRef.child(keyTaiKhoan).removeValue((error, ref) -> {
            if (error != null) {
                future.completeExceptionally(new RuntimeException("Lỗi khi xoá Khách Hàng: " + error.getMessage()));
            } else {
                // Xoá Tài Khoản từ bảng "TaiKhoan"
                taiKhoanRef.child(keyTaiKhoan).removeValue((error2, ref2) -> {
                    if (error2 != null) {
                        future.completeExceptionally(new RuntimeException("Lỗi khi xoá Tài Khoản: " + error2.getMessage()));
                    } else {
                        future.complete("Xoá thành công!");
                    }
                });
            }
        });

        return future;
    }


}
