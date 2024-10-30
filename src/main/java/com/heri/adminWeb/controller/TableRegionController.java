package com.heri.adminWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TableRegionController {
    @GetMapping("/table")
    public String table() {
        return "table_region/show";
    }

    @GetMapping("/table/add")
    public String add() {
        return "table_region/add_table";
    }

    @GetMapping("/table/edit")
    public String edit() {
        return "table_region/edit_table";
    }

    @GetMapping("/table/add_region")
    public String add_region() {
        return "table_region/add_region";
    }

    @GetMapping("/region/edit")
    public String edit_region() {
        return "table_region/edit_region";
    }
}
