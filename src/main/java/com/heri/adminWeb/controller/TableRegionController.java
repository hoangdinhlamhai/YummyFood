package com.heri.adminWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TableRegionController {
    @GetMapping("/table")
    public String table() {
        return "table_region/show";
    }
}
