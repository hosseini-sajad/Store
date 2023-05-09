package com.example.store.service;

import com.example.store.core.StoreException;
import com.example.store.dto.CategoryDto;
import com.example.store.dto.CategoryListDto;
import com.example.store.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    void insertCategory(CategoryDto categoryDto) throws StoreException;

    Category getCategoryById(Integer id) throws StoreException;
    List<Category> getCategoriesById(Integer id) throws StoreException;

    List<CategoryListDto> findAllCategories();
}
