package com.example.yummyfood.Domain;

public class ChiTietDonHang {
    private int idChiTietDonHang; // ID của chi tiết đơn hàng
    private int idMonAn; // ID của món ăn
    private int soLuong; // Số lượng món ăn

    // Constructor
    public ChiTietDonHang(int idChiTietDonHang, int idMonAn, int soLuong) {
        this.idChiTietDonHang = idChiTietDonHang;
        this.idMonAn = idMonAn;
        this.soLuong = soLuong;
    }

    // Getters và Setters
    public int getIdChiTietDonHang() {
        return idChiTietDonHang;
    }

    public void setIdChiTietDonHang(int idChiTietDonHang) {
        this.idChiTietDonHang = idChiTietDonHang;
    }

    public int getIdMonAn() {
        return idMonAn;
    }

    public void setIdMonAn(int idMonAn) {
        this.idMonAn = idMonAn;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}