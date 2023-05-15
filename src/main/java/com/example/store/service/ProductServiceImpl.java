package com.example.store.service;

import com.example.store.core.StoreException;
import com.example.store.dto.CategoryListDto;
import com.example.store.dto.ProductDto;
import com.example.store.model.Category;
import com.example.store.model.Product;
import com.example.store.repository.ProductRepository;
import org.hibernate.event.internal.EntityState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryService categoryService;

    @Override
    public List<ProductDto> getProducts(Integer categoryId) throws StoreException {
        if (Objects.nonNull(categoryId)) {
            return productListToDto(productRepository.findAllByCategoryAndEntityState(categoryService.getCategoryById(categoryId), EntityState.PERSISTENT));
        }
        return productListToDto(productRepository.findAllByEntityState(EntityState.PERSISTENT));

    }

    private List<ProductDto> productListToDto(List<Product> products) {
        return products.stream().map(product -> ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .image(product.getImages().get(0))
                .price(product.getPrice()).build()).collect(Collectors.toList());
    }
}
