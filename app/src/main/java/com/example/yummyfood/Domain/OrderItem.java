package com.example.yummyfood.Domain;

public class OrderItem {
    private String tenMonAn;
    private int soLuong;
    private int giaMonAn;
    private String hinhAnh;

    public OrderItem(String tenMonAn, int soLuong, int giaMonAn,  String hinhAnh) {
        this.tenMonAn = tenMonAn;
        this.soLuong = soLuong;
        this.giaMonAn = giaMonAn;
        this.hinhAnh = hinhAnh;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaMonAn() {
        return giaMonAn;
    }

    public void setGiaMonAn(int giaMonAn) {
        this.giaMonAn = giaMonAn;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
