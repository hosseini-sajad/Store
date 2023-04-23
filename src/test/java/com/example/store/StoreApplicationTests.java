package com.example.store;

import com.example.store.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StoreApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
    }

    void createBackdoorUser() {
        if (userService.findAllUser().isEmpty()) {

        }
    }

}
