package com.example.store.controller;

import com.example.store.core.StoreException;
import com.example.store.service.CategoryService;
import com.example.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@RequestMapping(value = "product")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @GetMapping(value = {"", "/{categoryId}"})
    public ModelAndView getProducts(@PathVariable(required = false) Integer categoryId) {
        try {
            return new ModelAndView("product/index", new HashMap<>() {{
                put("products", productService.getProducts(categoryId));
                put("categories", categoryService.getHierarchyCategories());
            }});
        } catch (StoreException e) {
            return new ModelAndView("error", new HashMap<>(){{
                put("code", e.getCode());
                put("message", e.getMessage());
            }});
        }
    }

}
