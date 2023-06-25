package com.example.store.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductListDto {
    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer categoryId;
    // buy price
    private BigDecimal fee;
    private String image;
}
