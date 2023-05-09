package com.example.store.controller;

import com.example.store.core.StoreException;
import com.example.store.dto.CategoryDto;
import com.example.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public ModelAndView insertCategory() {
        return new ModelAndView("category/index", new HashMap<>() {{
            put("categoryDto", new CategoryDto());
            put("list", categoryService.findAllCategories());
        }});
    }

    @PostMapping("")
    public ModelAndView insertCategory(@ModelAttribute CategoryDto categoryDto) {
        try {
            categoryService.insertCategory(categoryDto);
            return new ModelAndView("redirect:/category");
        }catch (StoreException e) {
            return new ModelAndView("redirect:/");
        }
    }
}
