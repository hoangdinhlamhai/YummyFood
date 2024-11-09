package com.heri.adminWeb.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "DanhMuc")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDanhMuc;
    private String tenDanhMuc;

    @OneToMany(mappedBy = "category") //phải đúng với tên thuộc tính được ánh xạ của table bên kia(Food)
    private List<Food> foods;

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public long getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(long idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }
}
