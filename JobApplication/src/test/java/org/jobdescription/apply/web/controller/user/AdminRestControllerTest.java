package org.jobdescription.apply.web.controller.user;

import org.junit.jupiter.api.Test;
import org.jobdescription.apply.TestUtil;
import org.category.apply.model.Role;
import org.category.apply.model.User;
import org.category.apply.service.UserService;
import org.category.apply.util.exception.NotFoundException;
import org.category.apply.web.controller.user.AdminRestController;
import org.jobdescription.apply.web.controller.AbstractControllerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.jobdescription.apply.UserTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestController.REST_URL + "/";

    @Autowired
    private UserService service;

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void createWithLocation() throws Exception {
        User newUser = getNew();
        ResultActions action = perform(
                MockMvcRequestBuilders.post(REST_URL)
                                      .contentType(MediaType.APPLICATION_JSON)
                                      .content(jsonWithPassword(newUser, newUser.getPassword()))
        ).andDo(print())
         .andExpect(status().isCreated());

        User created = TestUtil.readFromJson(action, User.class);
        int newId = created.id();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + ADMIN_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(ADMIN));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void getByEmail() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "by/")
                                      .queryParam("email", ADMIN.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(ADMIN));
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(ADMIN, USER));
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void update() throws Exception {
        User updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + ADMIN_ID)
                                      .contentType(MediaType.APPLICATION_JSON)
                                      .content(jsonWithPassword(updated, updated.getPassword())))
                .andDo(print())
                .andExpect(status().isNoContent());

        USER_MATCHER.assertMatch(service.get(ADMIN_ID), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + USER_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void updateHtmlUnsafe() throws Exception {
        User updated = getUpdated();
        updated.setName("<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(REST_URL + ADMIN_ID)
                                      .contentType(MediaType.APPLICATION_JSON)
                                      .content(jsonWithPassword(updated, updated.getPassword())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void enable() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL + USER_ID)
                                      .param("enabled", "false")
                                      .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertFalse(service.get(USER_ID).isEnabled());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void createInvalid() throws Exception {
        User invalid = new User(null, null, "", "newPass", true, Role.USER, Role.ADMIN);
        perform(MockMvcRequestBuilders.post(REST_URL)
                                      .contentType(MediaType.APPLICATION_JSON)
                                      .content(jsonWithPassword(invalid, invalid.getPassword())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void updateInvalid() throws Exception {
        User invalid = getUpdated();
        invalid.setName("");
        perform(MockMvcRequestBuilders.put(REST_URL + ADMIN_ID)
                                      .contentType(MediaType.APPLICATION_JSON)
                                      .content(jsonWithPassword(invalid, invalid.getPassword())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        User newUser = getNew();
        newUser.setEmail("user@gmail.com");
        perform(MockMvcRequestBuilders.post(REST_URL)
                                      .contentType(MediaType.APPLICATION_JSON)
                                      .content(jsonWithPassword(newUser, newUser.getPassword())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        User updated = getUpdated();
        updated.setId(USER_ID + 1);
        updated.setEmail("new@gmail.com");
        perform(MockMvcRequestBuilders.put(REST_URL + ADMIN_ID)
                                      .contentType(MediaType.APPLICATION_JSON)
                                      .content(jsonWithPassword(updated, updated.getPassword())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}