package com.example.yummyfood.Domain;

public class Ban {
    private Long idKhuVuc; // Kiểu Long cho idKhuVuc
    private int soLuongGhe; // Kiểu int cho số lượng ghế
    private String tenBan; // Kiểu String cho tên bàn

    // Constructor trống
    public Ban() {}

    // Getter và Setter
    public Long getIdKhuVuc() {
        return idKhuVuc;
    }

    public void setIdKhuVuc(Long idKhuVuc) {
        this.idKhuVuc = idKhuVuc;
    }

    public int getSoLuongGhe() {
        return soLuongGhe;
    }

    public void setSoLuongGhe(int soLuongGhe) {
        this.soLuongGhe = soLuongGhe;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }
}