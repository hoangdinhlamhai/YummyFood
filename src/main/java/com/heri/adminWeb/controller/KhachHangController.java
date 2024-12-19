package com.heri.adminWeb.controller;

import com.heri.adminWeb.domain.KhachHang;
import com.heri.adminWeb.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class KhachHangController {
    @Autowired
    private KhachHangService khachHangService;

    @GetMapping("/user")
    public String user(Model model) {
        try {
            // Gọi Service để lấy dữ liệu
            List<KhachHang> accountList = khachHangService.getAllTaiKhoanWithKhachHang().get();

            // Đẩy dữ liệu vào Model để truyền sang JSP
            model.addAttribute("khachHangList", accountList);

            return "user/show";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error fetching data: " + e.getMessage());
            return "errorPage";
        }
    }

    @GetMapping("/user/add")
    public String showAddKhachHangForm(Model model) {
        try {
            // Tạo một đối tượng KhachHang rỗng để bind dữ liệu từ form
            model.addAttribute("newKhachHang", new KhachHang());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi khi hiển thị form thêm khách hàng.");
        }
        return "user/add";
    }

    @PostMapping("user/add")
    public String addKhachHang(@ModelAttribute("newKhachHang") KhachHang khachHang,
                               RedirectAttributes redirectAttributes) {
        try {
            // Gọi service để thêm khách hàng và tài khoản
            khachHangService.addKhachHangWithTaiKhoan(khachHang);
            redirectAttributes.addFlashAttribute("message", "Thêm khách hàng thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm khách hàng.");
        }
        return "redirect:/user"; // Chuyển hướng về trang danh sách khách hàng
    }

    @GetMapping("/user/edit/{id}")
    public String showEditForm(@PathVariable("id") String keyTaiKhoan, Model model) {
        try {
            // Gọi service để lấy thông tin khách hàng
            KhachHang khachHang = khachHangService.findKhachHangById(keyTaiKhoan).get();
            model.addAttribute("khachHang", khachHang);
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi lấy thông tin khách hàng: " + e.getMessage());
        }
        return "user/edit"; // Tên file JSP
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") String keyTaiKhoan,
                             @ModelAttribute("khachHang") KhachHang khachHang,
                             RedirectAttributes redirectAttributes) {
        try {
            khachHang.setKeyTaiKhoan(keyTaiKhoan); // Giữ keyTaiKhoan cố định
            khachHangService.updateKhachHangWithTaiKhoan(khachHang);
            redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật thông tin.");
        }
        return "redirect:/user";
    }

    @PostMapping("/user/delete/{id}")
    public String deleteKhachHang(@PathVariable("id") String keyTaiKhoan, RedirectAttributes redirectAttributes) {
        try {
            // Gọi Service để xoá khách hàng
            khachHangService.deleteKhachHang(keyTaiKhoan).get();

            // Thông báo xoá thành công
            redirectAttributes.addFlashAttribute("message", "Xoá khách hàng thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xoá khách hàng!");
        }

        return "redirect:/user";
    }
}
