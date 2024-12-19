package com.heri.adminWeb.domain;

public class Ban {
    private String keyBan, tenBan;
    private int keyKhuVuc, soLuongGhe;

    public Ban() {}

    public Ban(String keyBan, String tenBan, int keyKhuVuc, int soLuongGhe) {
        this.keyBan = keyBan;
        this.tenBan = tenBan;
        this.keyKhuVuc = keyKhuVuc;
        this.soLuongGhe = soLuongGhe;
    }

    public String getKeyBan() {
        return keyBan;
    }

    public void setKeyBan(String keyBan) {
        this.keyBan = keyBan;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public int getKeyKhuVuc() {
        return keyKhuVuc;
    }

    public void setKeyKhuVuc(int keyKhuVuc) {
        this.keyKhuVuc = keyKhuVuc;
    }

    public int getSoLuongGhe() {
        return soLuongGhe;
    }

    public void setSoLuongGhe(int soLuongGhe) {
        this.soLuongGhe = soLuongGhe;
    }

    @Override
    public String toString() {
        return "Ban{" +
                "keyBan='" + keyBan + '\'' +
                ", tenBan='" + tenBan + '\'' +
                ", keyKhuVuc=" + keyKhuVuc +
                ", soLuongGhe=" + soLuongGhe +
                '}';
    }
}
