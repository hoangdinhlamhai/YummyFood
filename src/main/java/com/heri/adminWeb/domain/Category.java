package com.heri.adminWeb.domain;

public class Category {
    private String idDanhMuc;
    private String tenDanhMuc;
    private String hinhAnh;

    public Category() {}

    public Category(String idDanhMuc, String tenDanhMuc, String hinhAnh) {
        this.idDanhMuc = idDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
        this.hinhAnh = hinhAnh;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(String idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }
}
