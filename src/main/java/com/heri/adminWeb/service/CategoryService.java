package com.heri.adminWeb.service;

import com.heri.adminWeb.domain.Category;
import com.heri.adminWeb.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    public void handleSaveCategory(Category category) {
        this.categoryRepository.save(category);
    }

    public void deleteCategory(long idDanhMuc) {
        this.categoryRepository.deleteById(idDanhMuc);
    }

    public Category findById(long idDanhMuc) {
        return this.categoryRepository.findById(idDanhMuc).orElse(null);
    }

    public void update(Category category) {
        this.categoryRepository.save(category); // Lưu lại danh mục sau khi chỉnh sửa
    }
}
