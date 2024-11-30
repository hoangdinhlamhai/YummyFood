package com.example.yummyfood.Domain;

public class Ban {
    private String tenBan;  // Tên bàn
    private String idBan;   // ID bàn (dùng String vì Firebase lưu dưới dạng String)
    private String idKhuVuc; // ID khu vực mà bàn thuộc về (dùng String vì Firebase lưu dưới dạng String)

    // Constructor rỗng cần thiết cho Firebase
    public Ban() {}

    // Constructor có tham số
    public Ban(String tenBan, String idBan, String idKhuVuc) {
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

    public String getIdBan() {
        return idBan;
    }

    public void setIdBan(String idBan) {
        this.idBan = idBan;
    }

    public String getIdKhuVuc() {
        return idKhuVuc;
    }

    public void setIdKhuVuc(String idKhuVuc) {
        this.idKhuVuc = idKhuVuc;
    }
}
