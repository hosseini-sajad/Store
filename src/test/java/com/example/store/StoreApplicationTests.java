package com.example.store;

import com.example.store.core.Gender;
import com.example.store.core.UserRole;
import com.example.store.model.User;
import com.example.store.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class StoreApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    void createBackdoorUser() {
        if (userService.findAllUser().isEmpty()) {
            User backdoorUser = User.builder().build();
            backdoorUser.setName("sajad");
            backdoorUser.setEmail("ss@yahoo.com");
            backdoorUser.setUsername("sajad");
            backdoorUser.setPassword("123456");
            backdoorUser.setIsActive(true);
            backdoorUser.setGender(Gender.MALE);
            backdoorUser.setPhone("09562652");
            backdoorUser.setIsFirstLogin(true);
            backdoorUser.setRole(UserRole.Backdoor);
            backdoorUser.setLastLogin(new Date());
            userService.insert(backdoorUser);
        }
    }

}
