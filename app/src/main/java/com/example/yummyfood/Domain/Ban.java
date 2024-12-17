package com.example.yummyfood.Domain;

public class Ban {
    private Long idKhuVuc; // ID khu vực
    private int soLuongGhe; // Số lượng ghế
    private String tenBan; // Tên bàn
    private String ngay; // Ngày đặt bàn
    private String gioBatDau; // Giờ bắt đầu
    private String gioKetThuc; // Giờ kết thúc

    // Constructor trống
    public Ban() {}

    // Getter và Setter cho idKhuVuc
    public Long getIdKhuVuc() {
        return idKhuVuc;
    }

    public void setIdKhuVuc(Long idKhuVuc) {
        this.idKhuVuc = idKhuVuc;
    }

    // Getter và Setter cho soLuongGhe
    public int getSoLuongGhe() {
        return soLuongGhe;
    }

    public void setSoLuongGhe(int soLuongGhe) {
        this.soLuongGhe = soLuongGhe;
    }

    // Getter và Setter cho tenBan
    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    // Getter và Setter cho ngay
    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    // Getter và Setter cho gioBatDau
    public String getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(String gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    // Getter và Setter cho gioKetThuc
    public String getGioKetThuc() {
        return gioKetThuc;
    }

    public void setGioKetThuc(String gioKetThuc) {
        this.gioKetThuc = gioKetThuc;
    }
}
