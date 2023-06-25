package com.example.store.service;

import com.example.store.core.StoreException;
import com.example.store.dto.ProductListDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ProductService {
    List<ProductListDto> getProducts(Integer categoryId) throws StoreException;

    void insertProduct(ProductListDto productListDto, MultipartFile... images) throws StoreException;
}
