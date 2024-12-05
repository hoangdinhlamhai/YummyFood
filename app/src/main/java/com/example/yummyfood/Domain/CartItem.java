package com.example.yummyfood.Domain;

public class CartItem {
    private int idChiTietDonHang;
    private int idMonAn;
    private int soLuong;


    public CartItem(int idChiTietDonHang, int idMonAn, int soLuong) {
        this.idChiTietDonHang = idChiTietDonHang;
        this.idMonAn = idMonAn;
        this.soLuong = soLuong;
    }

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
