package com.heri.adminWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashBoardController {
    @GetMapping("")
    public String dashboard() {
        return "index";
    }

    @GetMapping("/food/add")
    public String add() {
        return "food/add";
    }

    @GetMapping("/food/edit")
    public String edit() {
        return "food/edit";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile/show";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
