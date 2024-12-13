package com.example.yummyfood.Domain;

import java.util.List;

public class Order {
    private String orderId; // Thêm trường orderId
    private String orderTime;
    private String trangThai;
    private List<OrderItem> items;

    // Constructor
    public Order(String orderId, String orderTime, String status, List<OrderItem> items) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.trangThai = status;
        this.items = items;
    }

    // Getter và Setter
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String status) {
        this.trangThai = status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
