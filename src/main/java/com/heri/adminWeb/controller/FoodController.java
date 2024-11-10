package com.heri.adminWeb.controller;

import com.heri.adminWeb.domain.Category;
import com.heri.adminWeb.domain.Food;
import com.heri.adminWeb.repository.FoodRepository;
import com.heri.adminWeb.service.CategoryService;
import com.heri.adminWeb.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class FoodController {
    private final FoodService foodService;
    private final CategoryService categoryService;

    @Autowired
    public FoodController(FoodService foodService, CategoryService categoryService) {
        this.foodService = foodService;
        this.categoryService = categoryService;
    }

    @GetMapping("/food")
    public String food(Model model) {

        List<Food> foods = foodService.getAllFoods();
        model.addAttribute("foods", foods);

        return "food/show";
    }

    @GetMapping("/food/add")
    public String addFood(Model model) {
        model.addAttribute("newFood", new Food());

        // Lấy danh sách danh mục từ service và thêm vào model
        List<Category> categories = categoryService.getNameCategories();
        model.addAttribute("categories", categories);
        return "food/add";
    }

    @PostMapping("/food/add")
    public String addFood(@ModelAttribute("newFood") Food food) {
        foodService.saveFood(food); // Lưu đối tượng Food qua service
        return "redirect:/food"; // Điều hướng về danh sách món ăn
    }

    @GetMapping("/food/edit/{idMonAn}")
    public String edit(Model model, @PathVariable("idMonAn") long idMonAn) {
        Food food = foodService.getFoodById(idMonAn); // Lấy món ăn từ service
        model.addAttribute("food", food); // Thêm vào model để hiển thị trong form
        // Lấy danh sách danh mục từ service và thêm vào model
        List<Category> categories = categoryService.getNameCategories();
        model.addAttribute("categories", categories);
        return "food/edit";
    }

    @PostMapping("/food/update")
    public String updateFood(@ModelAttribute("food") Food food, RedirectAttributes redirectAttributes) {
        foodService.updateFood(food); // Gọi service để cập nhật món ăn
        redirectAttributes.addFlashAttribute("message", "Cập nhật món ăn thành công!");
        return "redirect:/food"; // Chuyển hướng về danh sách món ăn sau khi cập nhật
    }

    @PostMapping("/food/delete/{idMonAn}")
    public String deleteCategory(@PathVariable long idMonAn) {
        this.foodService.deleteFood(idMonAn);
        return "redirect:/food";  // Sau khi xóa, chuyển hướng lại về danh sách
    }
}
