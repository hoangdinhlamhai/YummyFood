package com.heri.adminWeb.controller;

import com.heri.adminWeb.domain.Ban;
import com.heri.adminWeb.domain.Category;
import com.heri.adminWeb.domain.Food;
import com.heri.adminWeb.domain.KhuVuc;
import com.heri.adminWeb.service.BanService;
import com.heri.adminWeb.service.CategoryService;
import com.heri.adminWeb.service.KhuVucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Controller
public class KhuVucController {
    @Autowired
    private KhuVucService khuVucService;

    @Autowired
    private BanService banService;

    @GetMapping("/table")
    public String loadTableRegion(Model model) {
        try {
            // Lấy danh sách Khu Vực từ Firebase
            CompletableFuture<List<KhuVuc>> futureKhuVuc = khuVucService.findAllAsync();
            // Lấy danh sách Bàn từ Firebase
            CompletableFuture<List<Ban>> futureBan = banService.findAllWithKhuVucAsync();

            // Chờ cả hai tác vụ hoàn thành
            List<KhuVuc> khuVucs = futureKhuVuc.get();
            List<Ban> banList = futureBan.get();

            // Tạo ánh xạ khu vực -> danh sách bàn
            Map<String, List<Ban>> khuVucToBanMap = banList.stream()
                    .collect(Collectors.groupingBy(ban -> String.valueOf(ban.getKeyKhuVuc())));

            // Đẩy dữ liệu vào model
            model.addAttribute("regions", khuVucs);
            model.addAttribute("regionToTablesMap", khuVucToBanMap);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi khi lấy dữ liệu từ Firebase.");
        }
        return "table_region/show";
    }

    @GetMapping("/table/add_region")
    public String showAddRegionForm(Model model) {
        model.addAttribute("newRegion", new KhuVuc());
        return "table_region/add_region"; // Tên file JSP
    }

    @PostMapping("/table/add_region")
    public String addCategory(@ModelAttribute("newRegion") KhuVuc khuVuc, RedirectAttributes redirectAttributes) {
        khuVucService.addKhuVuc(khuVuc);
        redirectAttributes.addFlashAttribute("message", "Thêm thành công!");
        return "redirect:/table"; // Quay lại danh sách danh mục
    }


    //update
    @GetMapping("/region/edit/{key}")
    public String showEditRegionForm(@PathVariable("key") String key, Model model) {
        try {
            KhuVuc khuVuc = khuVucService.findByKey(key);
            if (khuVuc != null) {
                model.addAttribute("region", khuVuc);
                model.addAttribute("key", key); // Gửi key xuống view để dùng khi submit
            } else {
                model.addAttribute("error", "Danh mục không tồn tại.");
                return "redirect:/table";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi khi tải danh mục.");
            return "redirect:/table";
        }
        return "table_region/edit_region"; // Tên file JSP
    }

    @PostMapping("/region/edit/{key}")
    public String updateRegion(@PathVariable("key") String key, @ModelAttribute("category") KhuVuc updatedKhuVuc, RedirectAttributes redirectAttributes) {
        khuVucService.updateKhuVuc(key, updatedKhuVuc);
        redirectAttributes.addFlashAttribute("message", "Cập nhật danh mục thành công!");
        return "redirect:/table";
    }

    //delete
    @PostMapping("/region/delete")
    public String delete(@RequestParam("id") String id, RedirectAttributes redirectAttributes) {
        khuVucService.deleteKhuVuc(id);
        redirectAttributes.addFlashAttribute("message", "Xóa thành công!");
        return "redirect:/table"; // Quay lại danh sách danh mục
    }
}
