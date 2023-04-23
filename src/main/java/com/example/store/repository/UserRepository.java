package com.example.store.repository;

import com.example.store.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("SELECT u from User u where u.username=?1 and u.password=?2")
    User login(String username, String password);
}
