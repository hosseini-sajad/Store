package com.example.store.dto;

import com.example.store.model.Category;
import com.example.store.model.Product;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Integer id;
    private String name;
    private String image;
    private Double price;
}
