package com.example.store.service;

import com.example.store.core.StoreException;
import com.example.store.model.Category;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    void insertCategory(Category category, Category parent) throws StoreException;
}
