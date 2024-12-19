package com.heri.adminWeb.domain;

public class TaiKhoan {
    private String keyTaiKhoan, tenTaiKhoan, matKhau;
    private KhachHang khachHang;

    public TaiKhoan(String keyTaiKhoan, String tenTaiKhoan, String matKhau) {
        this.keyTaiKhoan = keyTaiKhoan;
        this.tenTaiKhoan = tenTaiKhoan;
        this.matKhau = matKhau;
    }

    public String getKeyTaiKhoan() {
        return keyTaiKhoan;
    }

    public void setKeyTaiKhoan(String keyTaiKhoan) {
        this.keyTaiKhoan = keyTaiKhoan;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }
}
