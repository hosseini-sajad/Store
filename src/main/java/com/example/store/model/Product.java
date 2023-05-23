package com.example.store.model;

import com.example.store.model.base.BaseEntity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashMap;
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
    private BigDecimal price;
    @ElementCollection
    private HashMap<String, String> options;
    private String description;
    private Float rate;
    private BigDecimal fee;

    @ManyToOne
    private Category category;

    @ElementCollection
    // create a new table in db
    private List<String> images;


    @ElementCollection
    private List<String> tags;

}
