package com.example.yummyfood.Domain;
public class ChiTietDatBan {
    private String tenBan;
    private String ngay;
    private String ghiChu;
    private String thoiGianBatDau;
    private String thoiGianKetThuc;

    // Constructor không đối số để Firebase có thể tạo đối tượng
    public ChiTietDatBan() {
        // Firebase yêu cầu constructor không có đối số để ánh xạ dữ liệu
    }

    // Constructor với đầy đủ tham số
    public ChiTietDatBan(String tenBan, String ngay, String ghiChu, String thoiGianBatDau, String thoiGianKetThuc) {
        this.tenBan = tenBan;
        this.ngay = ngay;
        this.ghiChu = ghiChu;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    // Getter và Setter
    public String getTenBan() { return tenBan; }
    public String getNgay() { return ngay; }
    public String getGhiChu() { return ghiChu; }
    public String getThoiGianBatDau() { return thoiGianBatDau; }
    public String getThoiGianKetThuc() { return thoiGianKetThuc; }

    public void setTenBan(String tenBan) { this.tenBan = tenBan; }
    public void setNgay(String ngay) { this.ngay = ngay; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
    public void setThoiGianBatDau(String thoiGianBatDau) { this.thoiGianBatDau = thoiGianBatDau; }
    public void setThoiGianKetThuc(String thoiGianKetThuc) { this.thoiGianKetThuc = thoiGianKetThuc; }
}

