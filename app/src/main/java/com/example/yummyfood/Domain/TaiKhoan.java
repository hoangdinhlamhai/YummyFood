package com.example.yummyfood.Domain;

public class TaiKhoan {
    private String matKhau;
    private String tenTaiKhoan;

    public TaiKhoan() {
        // Constructor mặc định cho Firebase
    }

    public TaiKhoan(String matKhau, String tenTaiKhoan) {
        this.matKhau = matKhau;
        this.tenTaiKhoan = tenTaiKhoan;
    }

    // Getters và Setters
    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }
}