package com.example.yummyfood.Domain;

public class DanhGia {
    private String danhGia;
    private String tenKhachHang;

    public DanhGia(String danhGia, String tenKhachHang) {
        this.danhGia = danhGia;
        this.tenKhachHang = tenKhachHang;
    }

    public String getDanhGia() {
        return danhGia;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }
}

