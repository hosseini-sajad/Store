package com.example.store.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryListDto {
    private Integer id;
    private String title;
    private List<CategoryListDto> children;
}
