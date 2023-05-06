package com.example.store.service;

import com.example.store.core.StoreException;
import com.example.store.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    void insertCategory(Category category, Category parent) throws StoreException;

    List<Category> findAllCategories();
}
