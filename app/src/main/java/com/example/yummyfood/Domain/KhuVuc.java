package com.example.yummyfood.Domain;

public class KhuVuc {private String tenKV; // Tên khu vực (Tầng)

    // Constructor rỗng cho Firebase
    public KhuVuc() {}

    // Constructor có tham số
    public KhuVuc(String tenKV) {
        this.tenKV = tenKV;
    }

    // Getter và Setter
    public String getTenKV() {
        return tenKV;
    }

    public void setTenKV(String tenKV) {
        this.tenKV = tenKV;
    }
}