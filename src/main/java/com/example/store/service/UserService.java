package com.example.store.service;

import com.example.store.core.StoreException;
import com.example.store.dto.UserDto;
import com.example.store.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User login(UserDto userDto) throws StoreException;

    List<User> findAllUser();
}
