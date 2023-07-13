package com.example.store.controller;

import com.example.store.core.StoreException;
import com.example.store.dto.ProductListDto;
import com.example.store.service.CategoryService;
import com.example.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@RequestMapping(value = "products")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @GetMapping(value = {"", "/category/{categoryId}"})
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
            put("productListDto", new ProductListDto());
            put("categories", categoryService.getHierarchyCategories());
        }});
    }

    @PostMapping(value = "/add")
    public ModelAndView insertProduct(@ModelAttribute ProductListDto productListDto, @RequestParam MultipartFile image1, @RequestParam MultipartFile image2) {
        try {
            productService.insertProduct(productListDto, image1, image2);
            return new ModelAndView("redirect:/products/add");
        } catch (StoreException e) {
            return new ModelAndView("error", new HashMap<>() {{
                put("code", e.getCode());
                put("message", e.getMessage());
            }});
        }
    }

    @GetMapping(value = {"/{productId}"})
    public ModelAndView getProductDetail(@PathVariable Integer productId) {
        try {
            return new ModelAndView("productdetail/index", new HashMap<>() {{
                put("product", productService.getProduct(productId));
            }});
        } catch (StoreException e) {
            return new ModelAndView("error", new HashMap<>() {{
                put("code", e.getCode());
                put("message", e.getMessage());
            }});
        }
    }

}
