package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {

    @PostMapping("/category/get")
    public String showByCategory(@RequestParam("") String category){

        return category;
    }
}
