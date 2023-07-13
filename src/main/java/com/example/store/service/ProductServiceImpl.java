package com.example.store.service;

import com.example.store.StoreApplication;
import com.example.store.core.Error;
import com.example.store.core.StoreException;
import com.example.store.dto.ProductDto;
import com.example.store.dto.ProductListDto;
import com.example.store.model.Category;
import com.example.store.model.Product;
import com.example.store.repository.ProductRepository;
import org.hibernate.event.internal.EntityState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public List<ProductListDto> getProducts(Integer categoryId) throws StoreException {
        if (Objects.nonNull(categoryId)) {
            return productListToDto(productRepository.findAllByCategoryAndEntityState(categoryService.getCategoryById(categoryId), EntityState.PERSISTENT));
        }
        return productListToDto(productRepository.findAllByEntityState(EntityState.PERSISTENT));

    }

    @Override
    public ProductDto getProduct(Integer productId) throws StoreException {
        Product product = productRepository.findByIdAndEntityState(productId, EntityState.PERSISTENT);

        return productToProductDto(product);
    }

    @Override
    public void insertProduct(ProductListDto productListDto, MultipartFile... images) throws StoreException {
        validateProduct(productListDto);
        List<File> files = new ArrayList<>();
        if (images.length > 0) {
            try {
                files = ConvertMultipartToFile(images);
            } catch (Exception e) {
                throw new StoreException(Error.UPLOAD_FILE);
            }
        }
        Product product = productDtoToProduct(productListDto, files);
        productRepository.save(product);
    }

    private List<File> ConvertMultipartToFile(MultipartFile[] images) throws Exception {
        ClassPathResource resource = new ClassPathResource(File.separator + "target" + File.separator + "classes" + File.separator + "static" + File.separator + "images" + File.separator + "product", StoreApplication.class);
        File newDirectory = new File(resource.getPath());
        if (!newDirectory.exists()) {
            newDirectory.mkdirs();
        }
        newDirectory = newDirectory.toPath().toAbsolutePath().toFile();
        List<File> files = new ArrayList<>();
        for (MultipartFile image : images) {
            if (image.isEmpty()) {
                continue;
            }
            Integer postfix = null;
            File file;
            while (true) {
                String[] originalFilename = Objects.requireNonNull(image.getOriginalFilename().split("\\."));
                String type = originalFilename[originalFilename.length - 1];
                String filename = "";
                for (int i = 0; i < originalFilename.length - 1; i++) {
                    filename += originalFilename[i];
                }
                file = new File(newDirectory, filename + (Objects.nonNull(postfix) ? postfix : "") + "." + type);
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

    private Product productDtoToProduct(ProductListDto productListDto, List<File> files) throws StoreException {
        Category category = categoryService.getCategoryById(productListDto.getCategoryId());
        Product product = Product.builder()
                .name(productListDto.getName())
                .price(productListDto.getPrice())
                .images(files.isEmpty() ? null : files.stream().map(File::getAbsolutePath).collect(Collectors.toList()))
                .category(category).build();
        product.setEntityState(EntityState.PERSISTENT);

        return product;
    }

    private void validateProduct(ProductListDto productListDto) throws StoreException {
        if (productListDto.getName().isBlank()) {
            throw new StoreException(Error.ERROR_PRODUCT_NAME);
        }
        if (productListDto.getName().length() < 2) {
            throw new StoreException(Error.ERROR0_PRODUCT_NAME_LENGTH);
        }
        if (productListDto.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new StoreException(Error.ERROR0_PRODUCT_PRICE_COUNT);
        }
    }

    private List<ProductListDto> productListToDto(List<Product> products) {
        List<ProductListDto> list = new ArrayList<>();
        for (Product product : products) {
            String productsPath = "";
            try {
                ClassPathResource resource = new ClassPathResource(File.separator + "target" + File.separator + "classes" + File.separator + "static", StoreApplication.class);
                File newDirectory = new File(resource.getPath()).toPath().toAbsolutePath().toFile();
                Path path = newDirectory.toPath();
                Path relativize = path.relativize(Paths.get(product.getImages().get(0)));
                productsPath = relativize.toString();
            } catch (Exception e) {

            }
            ProductListDto build = ProductListDto.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .image(productsPath)
                    .build();
            list.add(build);
        }
        return list;
    }

    private ProductDto productToProductDto(Product product) {
        String productsPath = "";
        try {
            ClassPathResource resource = new ClassPathResource(File.separator + "target" + File.separator + "classes" + File.separator + "static", StoreApplication.class);
            File newDirectory = new File(resource.getPath()).toPath().toAbsolutePath().toFile();
            Path path = newDirectory.toPath();
            Path relativize = path.relativize(Paths.get(product.getImages().get(0)));
            productsPath = relativize.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .options(product.getOptions())
                .description(product.getDescription())
                .rate(product.getRate())
                .fee(product.getFee())
                .category(product.getCategory())
                .images(productsPath)
                .tags(product.getTags())
                .build();
    }
}
