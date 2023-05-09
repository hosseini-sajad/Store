package com.example.store.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Integer id;
    private String title;
    private Integer parentId;
}
