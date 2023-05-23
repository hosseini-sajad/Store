package com.example.store.service;

import com.example.store.core.StoreException;
import com.example.store.dto.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ProductService {
    List<ProductDto> getProducts(Integer categoryId) throws StoreException;

    void insertProduct(ProductDto productDto, MultipartFile... images) throws StoreException;
}
