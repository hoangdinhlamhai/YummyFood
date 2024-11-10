package com.heri.adminWeb.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "MonAn")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMonAn;
    private String tenMonAn;
    private String moTa;
    private String hinhAnh;
    private String soLuong;
    private double donGia;

    @ManyToOne
    @JoinColumn(name = "idDanhMuc") //khoá ngoại idDanhMuc
    private Category category;

    public long getIdMonAn() {
        return idMonAn;
    }

    public void setIdMonAn(long idMonAn) {
        this.idMonAn = idMonAn;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
