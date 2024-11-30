package com.example.yummyfood.Domain;

public class CartItem {
    private int idChiTietDonHang;
    private int idMonAn;

    public CartItem(int idChiTietDonHang, int idMonAn) {
        this.idChiTietDonHang = idChiTietDonHang;
        this.idMonAn = idMonAn;
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
}
