package com.example.yummyfood.Domain;

import java.util.List;

public class Order {
    private String orderTime;
    private List<OrderItem> items; // Sửa đổi để lưu danh sách OrderItem

    public Order(String orderTime, List<OrderItem> items) {
        this.orderTime = orderTime;
        this.items = items;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public List<OrderItem> getItems() {
        return items;
    }
}
