package com.example.yummyfood.Domain;

public class KhachHang {
    private String email;
    private String idDiaChi;
    private String sdt;
    private String tenKhachHang;
    private String idTaiKhoan;

    public KhachHang() {
        // Constructor mặc định cho Firebase
    }

    public KhachHang(String email, String tenKhachHang, String idTaiKhoan) {
        this.email = email;
        this.tenKhachHang = tenKhachHang;
        this.idTaiKhoan = idTaiKhoan;
        this.idDiaChi = null; // Giữ các trường khác là null
        this.sdt = null; // Giữ các trường khác là null
    }

    // Getters và Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdDiaChi() {
        return idDiaChi;
    }

    public void setIdDiaChi(String idDiaChi) {
        this.idDiaChi = idDiaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }


    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(String idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }
}