package com.heri.adminWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {
    @GetMapping("/category")
    public String category() {
        return "category/show";
    }

    @GetMapping("/category/add")
    public String addCategory() {
        return "category/add";
    }

    @GetMapping("/category/edit")
    public String editCategory() {
        return "category/edit";
    }
}
