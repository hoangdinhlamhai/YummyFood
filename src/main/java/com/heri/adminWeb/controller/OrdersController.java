package com.heri.adminWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrdersController {
    @GetMapping("/orders")
    public String orders(Model model) {
        return "orders/show";
    }

    @GetMapping("/orders/details")
    public String details(Model model) {
        return "orders/details";
    }
}
