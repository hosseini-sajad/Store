package com.example.store.repository;

import com.example.store.model.Category;
import org.hibernate.event.internal.EntityState;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    List<Category> findAllByEntityState(EntityState entityState);

    List<Category> findAllByIdAndEntityState(Integer id, EntityState entityState);
    Optional<Category> findByIdAndEntityState(Integer id, EntityState entityState);
}
