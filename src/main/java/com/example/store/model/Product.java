package com.example.store.model;

import com.example.store.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {
    private String name;
    private Double price;
    private Integer size;
    private String description;
    private Double rate;

    @ManyToOne
    private Category category;

    @ElementCollection
    private List<String> images;


    @ElementCollection
    private List<String> tags;

}
