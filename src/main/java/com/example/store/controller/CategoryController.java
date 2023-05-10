package com.example.store.controller;

import com.example.store.core.StoreException;
import com.example.store.dto.CategoryDto;
import com.example.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
            put("list", categoryService.getHierarchyCategories());
        }});
    }

    @PostMapping("")
    public ModelAndView insertCategory(@ModelAttribute CategoryDto categoryDto) {
        try {
            if (categoryDto.getParentId().equals(-1)) {
                categoryDto.setParentId(null);
            }
            categoryService.insertCategory(categoryDto);
            return new ModelAndView("redirect:/category");
        }catch (StoreException e) {
            return new ModelAndView("redirect:/");
        }
    }
}
