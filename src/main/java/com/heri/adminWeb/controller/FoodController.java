package com.heri.adminWeb.controller;

import com.heri.adminWeb.domain.Category;
import com.heri.adminWeb.domain.Food;
import com.heri.adminWeb.service.CategoryService;
import com.heri.adminWeb.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
public class FoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/food")
    public String food(Model model) {
        try {
            // Lấy danh sách món ăn từ Firebase
            CompletableFuture<List<Food>> futureMonAn = foodService.findAllWithCategoryAsync();
            List<Food> monAnList = futureMonAn.join(); // Chờ cho CompletableFuture hoàn thành
            model.addAttribute("foods", monAnList); // Đẩy dữ liệu vào model
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi khi lấy dữ liệu từ Firebase.");
        }
        return "food/show";
    }

    @GetMapping("/food/add")
    public String showAddMonAnForm(Model model) {
        try {
            // Lấy danh sách danh mục để hiển thị trong combobox
            List<Category> categories = categoryService.findAllAsync().get();
            model.addAttribute("categories", categories); // Đưa danh mục vào model
            model.addAttribute("newMonAn", new Food());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi khi lấy danh mục từ Firebase.");
        }
        return "food/add"; // Tên file JSP
    }

    @PostMapping("/food/add")
    public String addMonAn(@ModelAttribute("newMonAn") Food monAn, RedirectAttributes redirectAttributes) {
        try {
            foodService.addMonAn(monAn);
            redirectAttributes.addFlashAttribute("message", "Thêm món ăn thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm món ăn.");
        }
        return "redirect:/food";
    }

    // Hiển thị form sửa món ăn
    @GetMapping("/food/edit/{id}")
    public String showEditFoodForm(@PathVariable("id") String id, Model model) {
        try {
            Food monAn = foodService.findById(id).get();
            List<Category> categories = categoryService.findAllAsync().get();
            model.addAttribute("food", monAn);
            model.addAttribute("categories", categories);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi khi lấy thông tin món ăn hoặc danh mục.");
            return "redirect:/food";
        }
        return "food/edit";
    }

    // Cập nhật món ăn
    @PostMapping("/food/update/{id}")
    public String editFood(@ModelAttribute("food") Food monAn, RedirectAttributes redirectAttributes) {
        try {
            foodService.updateMonAn(monAn);
            redirectAttributes.addFlashAttribute("message", "Cập nhật món ăn thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật món ăn.");
        }
        return "redirect:/food";
    }

    //delete
    @PostMapping("/food/delete")
    public String deleteCategory(@RequestParam("id") String keyMonAn, RedirectAttributes redirectAttributes) {
        foodService.deleteFood(keyMonAn);
        redirectAttributes.addFlashAttribute("message", "Xóa danh mục thành công!");
        return "redirect:/food"; // Quay lại danh sách danh mục
    }
}
