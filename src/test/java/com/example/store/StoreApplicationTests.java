package com.example.store;

import com.example.store.core.Gender;
import com.example.store.core.StoreException;
import com.example.store.core.UserRole;
import com.example.store.model.User;
import com.example.store.service.UserService;
import org.hibernate.event.internal.EntityState;
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

    @Test
    void createBackdoorUser() {
        if (userService.findAllUser().isEmpty()) {
            User backdoorUser = User.builder()
                    .name("sajad")
                    .email("ss@yahoo.com")
                    .username("sajad")
                    .password("123456")
                    .isActive(true)
                    .gender(Gender.MALE)
                    .phone("09562652")
                    .isFirstLogin(true)
                    .role(UserRole.Backdoor)
                    .build();

            backdoorUser.setEntityState(EntityState.PERSISTENT);

            try {
                userService.signup(backdoorUser);
            } catch (StoreException e) {
                e.printStackTrace();
            }

        }
    }

}
