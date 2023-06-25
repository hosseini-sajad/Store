package com.example.store.model;

import com.example.store.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {
    private String name;
    private BigDecimal price;
    @ElementCollection
    @CollectionTable(name = "ProductOption",
            joinColumns = {@JoinColumn(name = "product", referencedColumnName = "id")})
    @MapKeyColumn(name = "optionsKey")
    @Column(name = "options")
    private Map<String, String> options = new HashMap<>();
    private String description;
    private Float rate;
    private BigDecimal fee;

    @ManyToOne
    private Category category;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "ProductImages", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "images", nullable = false)
    // create a new table in db
    private List<String> images;


    @ElementCollection
    private List<String> tags;

}
