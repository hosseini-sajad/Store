package com.example.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @GetMapping("")
    public ModelAndView insertCategory() {
        return new ModelAndView("category/index");
    }
}
