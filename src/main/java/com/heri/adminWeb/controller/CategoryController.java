package com.heri.adminWeb.controller;

import com.heri.adminWeb.domain.Category;
import com.heri.adminWeb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    public String category(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "category/show";
    }

    @GetMapping("/category/add")
    public String addCategory(Model model) {
        Category category = new Category();
        model.addAttribute("newCategory", category);
        return "category/add";
    }

    @PostMapping("/category/add")
    public String saveCategory(@ModelAttribute("newCategory") Category category) {
        this.categoryService.handleSaveCategory(category); // Sử dụng service để lưu
        return "redirect:/category";
    }

    @PostMapping("/category/delete/{idDanhMuc}")
    public String deleteCategory(@PathVariable long idDanhMuc) {
        this.categoryService.deleteCategory(idDanhMuc);
        return "redirect:/category";  // Sau khi xóa, chuyển hướng lại về danh sách
    }

    //hiện ra trang edit
    @GetMapping("/category/edit/{idDanhMuc}")
    public String editCategory(@PathVariable("idDanhMuc") long idDanhMuc, Model model) {
        Category category = categoryService.findById(idDanhMuc);
        model.addAttribute("category", category);
        return "category/edit";
    }

    // Cập nhật danh mục
    @PostMapping("/category/update/{idDanhMuc}")
    public String updateCategory(@PathVariable("idDanhMuc") long idDanhMuc, @ModelAttribute Category category) {
        category.setIdDanhMuc(idDanhMuc); // Đảm bảo idDanhMuc không thay đổi
        categoryService.update(category); // Gọi service để cập nhật dữ liệu
        return "redirect:/category"; // Sau khi cập nhật, chuyển đến trang danh sách
    }
}
