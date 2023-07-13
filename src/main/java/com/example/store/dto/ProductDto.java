package com.example.store.dto;

import com.example.store.model.Category;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Integer id;
    private String name;
    private BigDecimal price;
    private Map<String, String> options = new HashMap<>();
    private String description;
    private Float rate;
    private BigDecimal fee;
    private Category category;
    private List<String> images;
    private List<String> tags;
}
