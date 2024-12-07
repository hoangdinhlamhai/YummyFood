package com.heri.adminWeb.controller;

import com.heri.adminWeb.domain.Category;
import com.heri.adminWeb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public String category(Model model) {
        try {
            // Lấy danh sách danh mục từ Firebase
            List<Category> categories = categoryService.findAllAsync().get();
            model.addAttribute("categories", categories); // Đẩy dữ liệu vào model
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi khi lấy dữ liệu từ Firebase.");
        }
        return "category/show";
    }

    @GetMapping("/category/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("newCategory", new Category());
        return "category/add"; // Tên file JSP
    }

    @PostMapping("/category/add")
    public String addCategory(@ModelAttribute("newCategory") Category category, RedirectAttributes redirectAttributes) {
        categoryService.addCategory(category);
        redirectAttributes.addFlashAttribute("message", "Thêm danh mục thành công!");
        return "redirect:/category"; // Quay lại danh sách danh mục
    }

    //update
    @GetMapping("/category/edit/{key}")
    public String showEditCategoryForm(@PathVariable("key") String key, Model model) {
        try {
            Category category = categoryService.findByKey(key);
            if (category != null) {
                model.addAttribute("category", category);
                model.addAttribute("key", key); // Gửi key xuống view để dùng khi submit
            } else {
                model.addAttribute("error", "Danh mục không tồn tại.");
                return "redirect:/category";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi khi tải danh mục.");
            return "redirect:/category";
        }
        return "category/edit"; // Tên file JSP
    }

    @PostMapping("/category/edit/{key}")
    public String updateCategory(@PathVariable("key") String key, @ModelAttribute("category") Category updatedCategory, RedirectAttributes redirectAttributes) {
        categoryService.updateCategory(key, updatedCategory);
        redirectAttributes.addFlashAttribute("message", "Cập nhật danh mục thành công!");
        return "redirect:/category";
    }


    //delete
    @PostMapping("/category/delete")
    public String deleteCategory(@RequestParam("id") String idDanhMuc, RedirectAttributes redirectAttributes) {
        System.out.println("idDanhMuc: " + idDanhMuc);
        categoryService.deleteCategory(idDanhMuc);
        redirectAttributes.addFlashAttribute("message", "Xóa danh mục thành công!");
        return "redirect:/category"; // Quay lại danh sách danh mục
    }

}
