package com.example.store.service;

import com.example.store.core.Error;
import com.example.store.core.StoreException;
import com.example.store.dto.ProductDto;
import com.example.store.model.Category;
import com.example.store.model.Product;
import com.example.store.repository.ProductRepository;
import org.eclipse.tags.shaded.org.apache.bcel.generic.IF_ACMPEQ;
import org.hibernate.event.internal.EntityState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void insertProduct(ProductDto productDto) throws StoreException {
        validateProduct(productDto);
        Product product = productDtoToProduct(productDto);
        productRepository.save(product);
    }

    private Product productDtoToProduct(ProductDto productDto) throws StoreException {
        Category category = categoryService.getCategoryById(productDto.getCategoryId());
        Product product = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .category(category)
                .images(productDto.getImages()).build();
        product.setEntityState(EntityState.PERSISTENT);

        return product;
    }

    private void validateProduct(ProductDto productDto) throws StoreException {
        if (productDto.getName().isBlank()) {
            throw new StoreException(Error.ERROR_PRODUCT_NAME);
        }
        if (productDto.getName().length() < 2) {
            throw new StoreException(Error.ERROR0_PRODUCT_NAME_LENGTH);
        }
        if (productDto.getPrice() < 0) {
            throw new StoreException(Error.ERROR0_PRODUCT_PRICE_COUNT);
        }
    }

    private List<ProductDto> productListToDto(List<Product> products) {
        return products.stream().map(product -> ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .images(product.getImages())
                .price(product.getPrice()).build()).collect(Collectors.toList());
    }
}
