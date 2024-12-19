package com.heri.adminWeb.domain;

public class KhuVuc {
    private String keyKhuVuc;
    private String tenKV;

    public KhuVuc() {}

    public KhuVuc(String keyKhuVuc, String tenKV) {
        this.keyKhuVuc = keyKhuVuc;
        this.tenKV = tenKV;
    }

    public String getKeyKhuVuc() {
        return keyKhuVuc;
    }

    public void setKeyKhuVuc(String keyKhuVuc) {
        this.keyKhuVuc = keyKhuVuc;
    }

    public String getTenKV() {
        return tenKV;
    }

    public void setTenKV(String tenKV) {
        this.tenKV = tenKV;
    }

    @Override
    public String toString() {
        return "KhuVuc{" +
                "keyKhuVuc='" + keyKhuVuc + '\'' +
                ", tenKV='" + tenKV + '\'' +
                '}';
    }
}
