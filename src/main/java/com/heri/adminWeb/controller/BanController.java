package com.heri.adminWeb.controller;

import com.heri.adminWeb.domain.Ban;
import com.heri.adminWeb.domain.Category;
import com.heri.adminWeb.domain.Food;
import com.heri.adminWeb.domain.KhuVuc;
import com.heri.adminWeb.service.BanService;
import com.heri.adminWeb.service.KhuVucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
public class BanController {
    @Autowired
    private BanService banService;

    @Autowired
    private KhuVucService khuVucService;

    @GetMapping("/table/add")
    public String showAddForm(Model model) {
        try {
            // Lấy danh sách danh mục để hiển thị trong combobox
            List<KhuVuc> khuVucs = khuVucService.findAllAsync().get();
            model.addAttribute("regions", khuVucs); // Đưa danh mục vào model
            model.addAttribute("newTable", new Ban());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi khi lấy danh mục từ Firebase.");
        }
        return "table_region/add_table"; // Tên file JSP
    }

    @PostMapping("/table/add")
    public String addBan(@ModelAttribute("newTable") Ban ban, RedirectAttributes redirectAttributes) {
        try {
            banService.addBan(ban);
            redirectAttributes.addFlashAttribute("message", "Thêm bàn thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm.");
        }
        return "redirect:/table";
    }

    // Hiển thị form sửa
    @GetMapping("/table/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        try {
            Ban ban  = banService.findById(id).get();
            List<KhuVuc> khuVucs = khuVucService.findAllAsync().get();

            // In ra giá trị
            System.out.println("Ban: " + id);
            System.out.println("Danh sách KhuVuc:");
            for (KhuVuc khuVuc : khuVucs) {
                System.out.println(khuVuc);
            }

            model.addAttribute("table", ban);
            model.addAttribute("regions", khuVucs);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi khi lấy thông tin món ăn hoặc danh mục.");
            return "redirect:/table";
        }
        return "table_region/edit_table";
    }

    // Cập nhật
    @PostMapping("/table/update/{id}")
    public String editTable(@ModelAttribute("table") Ban ban, RedirectAttributes redirectAttributes) {
        try {
            banService.updateBan(ban);
            redirectAttributes.addFlashAttribute("message", "Cập nhật thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật.");
        }
        return "redirect:/table";
    }

    //delete
    @PostMapping("/table/delete")
    public String deleteCategory(@RequestParam("id") String keyBan, RedirectAttributes redirectAttributes) {
        banService.delete(keyBan);
        redirectAttributes.addFlashAttribute("message", "Xóa thành công!");
        return "redirect:/table"; // Quay lại danh sách
    }
}
