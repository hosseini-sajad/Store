package com.example.store.dto;

import com.example.store.model.Category;
import com.example.store.model.Product;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer categoryId;
    // buy price
    private BigDecimal fee;
}
