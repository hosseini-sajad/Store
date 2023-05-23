package com.example.store.service;

import com.example.store.StoreApplication;
import com.example.store.core.Error;
import com.example.store.core.StoreException;
import com.example.store.dto.ProductDto;
import com.example.store.model.Category;
import com.example.store.model.Product;
import com.example.store.repository.ProductRepository;
import org.eclipse.tags.shaded.org.apache.bcel.generic.IF_ACMPEQ;
import org.hibernate.event.internal.EntityState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
    public void insertProduct(ProductDto productDto, MultipartFile... images) throws StoreException {
        validateProduct(productDto);
        List<File> files = ConvertMultipartToFile(images);
        Product product = productDtoToProduct(productDto, files);
        productRepository.save(product);
    }

    private List<File> ConvertMultipartToFile(MultipartFile[] images) {
        ClassLoader classLoader = StoreApplication.class.getClassLoader();
        try {
            Files.createDirectory(Paths.get(classLoader.getResource("images" + File.separator + "product").toURI()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        List<File> files = new ArrayList<>();
        Arrays.stream(images).forEach(image -> {
            if (image.isEmpty()) {
                return;
            }
            try {
                Path file = Files.createFile(Paths.get(classLoader.getResource("images" + File.separator + "product" + File.separator + image.getOriginalFilename()).toURI()));
                image.transferTo(file);
                files.add(file.toFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });
        return files;
    }

    private Product productDtoToProduct(ProductDto productDto, List<File> files) throws StoreException {
        Category category = categoryService.getCategoryById(productDto.getCategoryId());
        Product product = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .images(files.isEmpty() ? null : files.stream().map(file -> file.getAbsolutePath()).collect(Collectors.toList()))
                .category(category).build();
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
        if (productDto.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new StoreException(Error.ERROR0_PRODUCT_PRICE_COUNT);
        }
    }

    private List<ProductDto> productListToDto(List<Product> products) {
        return products.stream().map(product -> ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build())
                .collect(Collectors.toList());
    }
}
