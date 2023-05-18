package com.example.store.controller;

import com.example.store.core.Error;
import com.example.store.core.StoreException;
import com.example.store.dto.ProductDto;
import com.example.store.service.CategoryService;
import com.example.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Objects;

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
            return new ModelAndView("error", new HashMap<>() {{
                put("code", e.getCode());
                put("message", e.getMessage());
            }});
        }
    }

    @GetMapping(value = "/add")
    public ModelAndView insertProduct() {
        return new ModelAndView("product/add_product", new HashMap<>() {{
            put("productDto", new ProductDto());
            put("categories", categoryService.getHierarchyCategories());
        }});
    }

    @PostMapping(value = "/add")
    public ModelAndView insertProduct(@ModelAttribute ProductDto productDto) {
        try {
            productService.insertProduct(productDto);
            return new ModelAndView("redirect:/product/add");
        } catch (StoreException e) {
            return new ModelAndView("error", new HashMap<>() {{
                put("code", e.getCode());
                put("message", e.getMessage());
            }});
        }
    }

}
