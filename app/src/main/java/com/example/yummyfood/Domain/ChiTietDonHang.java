package com.example.yummyfood.Domain;

public class ChiTietDonHang {
    private int idChiTietDonHang; // ID của chi tiết đơn hàng
    private int idMonAn; // ID của món ăn
    private int soLuong; // Số lượng món ăn
    private String tenMonAn; // Tên món ăn (nếu cần hiển thị tên)
    private int giaMonAn; // Giá món ăn (nếu cần tính tổng giá)

    // Constructor đầy đủ
    public ChiTietDonHang(int idChiTietDonHang, int idMonAn, int soLuong, String tenMonAn, int giaMonAn) {
        this.idChiTietDonHang = idChiTietDonHang;
        this.idMonAn = idMonAn;
        this.soLuong = soLuong;
        this.tenMonAn = tenMonAn;
        this.giaMonAn = giaMonAn;
    }

    // Constructor đơn giản
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

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public int getGiaMonAn() {
        return giaMonAn;
    }

    public void setGiaMonAn(int giaMonAn) {
        this.giaMonAn = giaMonAn;
    }

    // Tính tổng giá trị của món ăn dựa trên số lượng
    public int getTongGia() {
        return soLuong * giaMonAn;
    }


}
