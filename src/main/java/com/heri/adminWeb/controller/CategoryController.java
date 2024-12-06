package com.heri.adminWeb.controller;

import com.heri.adminWeb.domain.Category;
import com.heri.adminWeb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    //delete
    @PostMapping("/category/delete")
    public String deleteCategory(@RequestParam("id") String idDanhMuc, RedirectAttributes redirectAttributes) {
        System.out.println("idDanhMuc: " + idDanhMuc);
        categoryService.deleteCategory(idDanhMuc);
        redirectAttributes.addFlashAttribute("message", "Xóa danh mục thành công!");
        return "redirect:/category"; // Quay lại danh sách danh mục
    }

}
