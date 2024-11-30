package com.example.yummyfood.Domain;

public class Ban {
    private String tenBan; // Tên bàn
    private int idBan; // ID bàn (kiểu int nếu ID là số nguyên)
    private int idKhuVuc; // ID khu vực mà bàn thuộc về (thêm thuộc tính này)

    // Constructor rỗng cần thiết cho Firebase
    public Ban() {}

    // Constructor có tham số
    public Ban(String tenBan, int idBan, int idKhuVuc) {
        this.tenBan = tenBan;
        this.idBan = idBan;
        this.idKhuVuc = idKhuVuc;
    }

    // Getter và Setter
    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public int getIdBan() {
        return idBan;
    }

    public void setIdBan(int idBan) {
        this.idBan = idBan;
    }

    public int getIdKhuVuc() {
        return idKhuVuc;
    }

    public void setIdKhuVuc(int idKhuVuc) {
        this.idKhuVuc = idKhuVuc;
    }
}
