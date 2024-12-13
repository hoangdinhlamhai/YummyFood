package com.example.yummyfood.Domain;

public class ChiTietDatBan {
    private String tenBan;
    private String ngay;
    private String thoiGianBatDau; // Thời gian bắt đầu
    private String thoiGianKetThuc; // Thời gian kết thúc
    private String ghiChu;
    private String userId; // Thêm trường userId
    private String trangThai;

    // Constructor mặc định
    public ChiTietDatBan() {}

    // Constructor có tham số
    public ChiTietDatBan(String tenBan, String ngay, String ghiChu, String thoiGianBatDau, String thoiGianKetThuc, String userId, String trangThai) {
        this.tenBan = tenBan;
        this.ngay = ngay;
        this.ghiChu = ghiChu;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.userId = userId; // Gán giá trị cho userId
        this.trangThai = trangThai;

    }

    // Getter và Setter
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