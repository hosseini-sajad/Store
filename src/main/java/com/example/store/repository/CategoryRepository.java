package com.example.store.repository;

import com.example.store.model.Category;
import org.hibernate.event.internal.EntityState;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    List<Category> findAllByEntityState(EntityState entityState);

    List<Category> findAllByParentAndEntityState(Category parent, EntityState entityState);

    @Query("SELECT c from Category c where c.parent is null and c.entityState = ?1")
    List<Category> findAllParentByEntityState(EntityState entityState);
    Optional<Category> findByIdAndEntityState(Integer id, EntityState entityState);
}
