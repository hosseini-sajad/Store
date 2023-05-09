package com.example.store.service;

import com.example.store.core.Error;
import com.example.store.core.StoreException;
import com.example.store.dto.CategoryDto;
import com.example.store.dto.CategoryListDto;
import com.example.store.model.Category;
import com.example.store.repository.CategoryRepository;
import org.hibernate.event.internal.EntityState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public void insertCategory(CategoryDto categoryDto) throws StoreException {
        validateCategory(categoryDto);
        Category category = categoryWrapper(categoryDto);
        categoryRepository.save(category);
    }

    private Category categoryWrapper(CategoryDto categoryDto) throws StoreException {
        Category category = Category.builder().title(categoryDto.getTitle())
                .parent(Objects.nonNull(categoryDto.getParentId()) ? getCategoryById(categoryDto.getParentId()) : null).build();

        category.setEntityState(EntityState.PERSISTENT);
        return category;
    }

    @Override
    public Category getCategoryById(Integer id) throws StoreException {
        return categoryRepository.findByIdAndEntityState(id, EntityState.PERSISTENT).orElseThrow(() -> new StoreException(Error.ERROR_READ_CATEGORY));
//        return categoryRepository.findByIdAndEntityState(id, EntityState.PERSISTENT).orElse(null);
//        return categoryRepository.findByIdAndEntityState(id, EntityState.PERSISTENT).get();
    }

    @Override
    public List<Category> getCategoriesById(Integer id) {
        return categoryRepository.findAllByIdAndEntityState(id, EntityState.PERSISTENT);
    }

    @Override
    public List<CategoryListDto> findAllCategories() {
        List<Category> parents = categoryRepository.findAllByEntityState(EntityState.PERSISTENT);
        List<CategoryListDto> categoryListDtos = new ArrayList<>();
        parents.forEach(category ->
        {
            List<Category> children = null;
            if (Objects.nonNull(category.getParent())) {
                children = getCategoriesById(category.getParent().getId());
            }
            categoryListDtos.add(
                    CategoryListDto.builder()
                            .id(category.getId())
                            .title(category.getTitle())
                            .children(categoryListToDto(children)).build());
        });

        return categoryListDtos;
    }

    private List<CategoryListDto> categoryListToDto(List<Category> children) {
        if (Objects.nonNull(children)) {
            return children.stream().map(category ->
                    CategoryListDto.builder().id(category.getId()).title(category.getTitle()).build()).toList();
        }
        return null;
    }

    private void validateCategory(CategoryDto categoryDto) throws StoreException {
        if (categoryDto.getTitle().isBlank()) {
            throw new StoreException(Error.ERROR06);
        }
        if (categoryDto.getTitle().length() < 2) {
            throw new StoreException(Error.ERROR07);
        }
    }
}
