package com.example.yummyfood.Domain;

public class OrderItem {
    private String tenMonAn;
    private int soLuong;
    private int giaMonAn;

    public OrderItem(String tenMonAn, int soLuong, int giaMonAn) {
        this.tenMonAn = tenMonAn;
        this.soLuong = soLuong;
        this.giaMonAn = giaMonAn;
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
}
