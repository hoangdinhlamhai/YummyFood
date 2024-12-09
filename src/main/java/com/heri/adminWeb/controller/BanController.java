//package com.heri.adminWeb.controller;
//
//import com.heri.adminWeb.domain.Ban;
//import com.heri.adminWeb.domain.Food;
//import com.heri.adminWeb.service.BanService;
//import com.heri.adminWeb.service.KhuVucService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//
//@Controller
//public class BanController {
//    @Autowired
//    private BanService banService;
//
//    @Autowired
//    private KhuVucService khuVucService;
//
//    @GetMapping("/table")
//    public String ban(Model model) {
//        try {
//            CompletableFuture<List<Ban>> futureBan = banService.findAllWithKhuVucAsync();
//            List<Ban> banList = futureBan.join(); // Chờ cho CompletableFuture hoàn thành
//            model.addAttribute("tables", banList); // Đẩy dữ liệu vào model
//        } catch (Exception e) {
//            e.printStackTrace();
//            model.addAttribute("error", "Lỗi khi lấy dữ liệu từ Firebase.");
//        }
//        return "table_region/show";
//    }
//}
