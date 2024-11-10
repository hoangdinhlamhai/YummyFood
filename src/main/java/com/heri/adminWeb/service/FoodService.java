package com.heri.adminWeb.service;

import com.heri.adminWeb.domain.Food;
import com.heri.adminWeb.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {
    private final FoodRepository foodRepository;

    @Autowired
    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    public void saveFood(Food food) {
        foodRepository.save(food); // Lưu món ăn vào cơ sở dữ liệu
    }

    public Food getFoodById(long id) {
        return foodRepository.findById(id).orElse(null);
    }

    public void updateFood(Food food) {
        foodRepository.save(food);
    }

    public void deleteFood(long id) {
        foodRepository.deleteById(id);
    }
}
