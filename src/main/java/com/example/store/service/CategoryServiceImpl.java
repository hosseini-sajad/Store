package com.example.store.service;

import com.example.store.core.Error;
import com.example.store.core.StoreException;
import com.example.store.model.Category;
import com.example.store.repository.CategoryRepository;
import org.hibernate.event.internal.EntityState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public void insertCategory(Category category, Category parent) throws StoreException {
        validateCategory(category);
        fillCategory(category, parent);
        categoryRepository.save(category);
    }

    @Override
    public List<Category> findAllCategories() {
        return (ArrayList<Category>) categoryRepository.findAll();
    }

    private void fillCategory(Category category, Category parent) {
        category.setParent(parent);
        category.setEntityState(EntityState.PERSISTENT);
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
