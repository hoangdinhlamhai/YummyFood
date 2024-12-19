package com.heri.adminWeb.domain;

public class KhachHang {
    private String tenKhachHang, email, sdt, keyTaiKhoan, tenTaiKhoan, matKhau;

    public KhachHang() {

    }

    public KhachHang(String tenKhachHang, String email, String sdt, String keyTaiKhoan, String tenTaiKhoan, String matKhau) {
        this.tenKhachHang = tenKhachHang;
        this.email = email;
        this.sdt = sdt;
        this.keyTaiKhoan = keyTaiKhoan;
        this.tenTaiKhoan = tenTaiKhoan;
        this.matKhau = matKhau;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
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

    @Override
    public String toString() {
        return "KhachHang{" +
                "tenKhachHang='" + tenKhachHang + '\'' +
                ", email='" + email + '\'' +
                ", sdt='" + sdt + '\'' +
                ", keyTaiKhoan='" + keyTaiKhoan + '\'' +
                ", tenTaiKhoan='" + tenTaiKhoan + '\'' +
                ", matKhau='" + matKhau + '\'' +
                '}';
    }
}
