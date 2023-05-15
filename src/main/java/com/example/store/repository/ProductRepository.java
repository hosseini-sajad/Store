package com.example.store.repository;

import com.example.store.model.Category;
import com.example.store.model.Product;
import org.hibernate.event.internal.EntityState;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    List<Product> findAllByCategoryAndEntityState(Category category, EntityState entityState);

    List<Product> findAllByEntityState(EntityState entityState);
}
