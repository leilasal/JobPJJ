package org.jobdescription.apply.service;


import org.junit.jupiter.api.Test;
import org.category.apply.model.User;
import org.category.apply.service.UserService;
import org.category.apply.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.jobdescription.apply.UserTestData.*;

class UserServiceTest extends AbstractServiceTest {
    @Autowired
    private UserService service;


    @Test
    void get() {
        User user = service.get(USER_ID);
        USER_MATCHER.assertMatch(user, USER);
    }


    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }


    @Test
    void getByEmail() {
        User user = service.getByEmail("admin@gmail.com");
        USER_MATCHER.assertMatch(user, ADMIN);
    }


}