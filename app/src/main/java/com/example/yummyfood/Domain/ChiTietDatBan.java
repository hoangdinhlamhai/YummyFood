package com.example.yummyfood.Domain;
public class ChiTietDatBan {
    private String Ngay;
    private String danDo;
    private String idBan;
    private String thoiGianBatDau;
    private String thoiGianKetThuc;

    // Constructor
    public ChiTietDatBan() {
        // Cần thiết cho Firebase
    }

    public ChiTietDatBan(String ngay, String danDo, String idBan, String thoiGianBatDau, String thoiGianKetThuc) {
        this.Ngay = ngay;
        this.danDo = danDo;
        this.idBan = idBan;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    // Getter methods
    public String getNgay() {
        return Ngay;
    }

    public String getDanDo() {
        return danDo;
    }

    public String getIdBan() {
        return idBan;
    }

    public String getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public String getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }
}