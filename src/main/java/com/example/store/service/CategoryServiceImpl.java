package com.example.store.service;

import com.example.store.core.Error;
import com.example.store.core.StoreException;
import com.example.store.model.Category;
import com.example.store.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public void insertCategory(Category category, Category parent) throws StoreException {
        validateCategory(category);
        Category fillCategory = fillCategory(category, parent);
        categoryRepository.save(fillCategory);
    }

    private Category fillCategory(Category category, Category parent) {
        category.setParent(parent);
        return category;
    }

    private void validateCategory(Category category) throws StoreException {
        if (category.getName().isBlank()) {
            throw new StoreException(Error.ERROR06);
        }
        if (category.getName().length() < 2) {
            throw new StoreException(Error.ERROR07);
        }
    }
}
