package com.example.store.service;

import com.example.store.core.StoreException;
import com.example.store.dto.ProductDto;
import com.example.store.model.Category;
import org.hibernate.event.internal.EntityState;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<ProductDto> getProducts(Integer categoryId) throws StoreException;
}
