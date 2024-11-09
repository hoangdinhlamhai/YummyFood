package com.heri.adminWeb.controller;

import com.heri.adminWeb.domain.Food;
import com.heri.adminWeb.repository.FoodRepository;
import com.heri.adminWeb.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FoodController {
    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/food")
    public String food(Model model) {

        List<Food> foods = foodService.getAllFoods();
        model.addAttribute("foods", foods);

        return "food/show";
    }
}
