package com.example.store.service;

import com.example.store.StoreApplication;
import com.example.store.core.Error;
import com.example.store.core.StoreException;
import com.example.store.dto.ProductDto;
import com.example.store.model.Category;
import com.example.store.model.Product;
import com.example.store.repository.ProductRepository;
import org.hibernate.event.internal.EntityState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
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

    @Override
    public void insertProduct(ProductDto productDto, MultipartFile... images) throws StoreException {
        validateProduct(productDto);
        List<File> files = new ArrayList<>();
        if (images.length > 0) {
            try {
                files = ConvertMultipartToFile(images);
            } catch (Exception e) {
                throw new StoreException(Error.UPLOAD_FILE);
            }
        }
        Product product = productDtoToProduct(productDto, files);
        productRepository.save(product);
    }

    private List<File> ConvertMultipartToFile(MultipartFile[] images) throws Exception {
        File resourceDirectory = new File(Objects.requireNonNull(StoreApplication.class.getResource("")).toURI());
        File newDirectory = new File(resourceDirectory, "images" + File.separator + "products");
        if (!newDirectory.exists()) {
            newDirectory.mkdir();
        }
        List<File> files = new ArrayList<>();
        for (MultipartFile image : images) {
            if (image.isEmpty()) {
                continue;
            }
            Integer postfix = null;
            File file;
            while (true) {
                file = new File(newDirectory, Objects.requireNonNull(image.getOriginalFilename()) + (Objects.nonNull(postfix) ? postfix : ""));
                if (!file.exists()) {
                    if (file.createNewFile()) {
                        break;
                    }
                }
                postfix = (Objects.nonNull(postfix) ? postfix + 1 : 1);
            }
            image.transferTo(file);
            files.add(file);
        }
        return files;
    }

    private Product productDtoToProduct(ProductDto productDto, List<File> files) throws StoreException {
        Category category = categoryService.getCategoryById(productDto.getCategoryId());
        Product product = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .images(files.isEmpty() ? null : files.stream().map(File::getAbsolutePath).collect(Collectors.toList()))
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
