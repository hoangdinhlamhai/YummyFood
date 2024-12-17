package com.example.yummyfood.Domain;

public class ChiTietDatBan {
    private String id; // Khóa ID trên Firebase
    private String tenBan; // Tên bàn
    private String ngay; // Ngày đặt bàn
    private String thoiGianBatDau; // Thời gian bắt đầu
    private String thoiGianKetThuc; // Thời gian kết thúc
    private String ghiChu; // Ghi chú đặt bàn
    private String userId; // ID người dùng đặt bàn
    private String trangThai; // Trạng thái đặt bàn (ví dụ: Đã đặt, Đã hủy)

    // Constructor mặc định (bắt buộc khi làm việc với Firebase)
    public ChiTietDatBan() {}

    public ChiTietDatBan(String tenBan, String ngay, String ghiChu, String thoiGianBatDau,
                         String thoiGianKetThuc, String userId, String trangThai) {
        this.tenBan = tenBan;
        this.ngay = ngay;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.ghiChu = ghiChu;
        this.userId = userId;
        this.trangThai = trangThai;
    }



    // Getter và Setter cho các trường

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(String thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public String getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(String thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }


}
