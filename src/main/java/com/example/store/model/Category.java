package com.example.store.model;

import com.example.store.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends BaseEntity {
    private String name;

    @OneToMany(targetEntity = Category.class, mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Category> categories;

    @ManyToOne
    private Category parent;

    @OneToMany(targetEntity = Product.class, fetch = FetchType.LAZY)
    private List<Product> products;
}
