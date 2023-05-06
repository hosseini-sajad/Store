package com.example.store.controller;

import com.example.store.core.StoreException;
import com.example.store.model.Category;
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
            put("category", new Category());
        }});
    }

    @PostMapping("")
    public ModelAndView insertCategory(@ModelAttribute Category category) {
        try {
            categoryService.insertCategory(category, null);
            return new ModelAndView("redirect:/category");
        }catch (StoreException e) {
            return new ModelAndView("redirect:/");
        }
    }

//    @GetMapping("/getcategories")
//    public ModelAndView findAllCategories(@ModelAttribute ModelMap model) {
//        List<Category> categories = categoryService.findAllCategories();
//        model.put("categories", categories);
//        return ModelAndView("categor/index", categories);
//    }
}
