package com.example.yummyfood.Domain;

public class KhuVuc {
    private String idKhuVuc; // ID khu vực
    private String tenKV;    // Tên khu vực

    public KhuVuc() {}

    public KhuVuc(String idKhuVuc, String tenKV) {
        this.idKhuVuc = idKhuVuc;
        this.tenKV = tenKV;
    }

    public String getIdKhuVuc() {
        return idKhuVuc;
    }

    public void setIdKhuVuc(String idKhuVuc) {
        this.idKhuVuc = idKhuVuc;
    }

    public String getTenKV() {
        return tenKV;
    }

    public void setTenKV(String tenKV) {
        this.tenKV = tenKV;
    }
}