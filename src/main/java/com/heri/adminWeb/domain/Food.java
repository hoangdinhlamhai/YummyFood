package com.heri.adminWeb.domain;

public class Food {
    private String tenMonAn, hinhAnh, moTa, keyMonAn;
    private int idDanhMuc, donGia;
    private String tenDanhMuc;

    public Food() {}

    public Food(String keyMonAn, String tenMonAn, String hinhAnh, String moTa, int idDanhMuc, int donGia, String tenDanhMuc) {
        this.keyMonAn = keyMonAn;
        this.tenMonAn = tenMonAn;
        this.hinhAnh = hinhAnh;
        this.moTa = moTa;
        this.idDanhMuc = idDanhMuc;
        this.donGia = donGia;
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(int idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public String getKeyMonAn() {
        return keyMonAn;
    }

    public void setKeyMonAn(String keyMonAn) {
        this.keyMonAn = keyMonAn;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }
}
