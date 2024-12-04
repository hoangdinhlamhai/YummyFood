package com.example.yummyfood.Domain;

public class DiaChi {
    private String id;
    private String diaChi;
    private String idTaiKhoan;

    public DiaChi(String id, String diaChi, String idTaiKhoan) {
        this.id = id;
        this.diaChi = diaChi;
        this.idTaiKhoan = idTaiKhoan;
    }

    // Getters và Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getIdTaiKhoan() { return idTaiKhoan; }
    public void setIdTaiKhoan(String idTaiKhoan) { this.idTaiKhoan = idTaiKhoan; }
}
