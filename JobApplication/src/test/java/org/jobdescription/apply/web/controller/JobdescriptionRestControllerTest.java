package org.jobdescription.apply.web.controller;

import org.junit.jupiter.api.Test;
import org.jobdescription.apply.TestUtil;
import org.category.apply.model.Jobdescription;
import org.category.apply.service.JobdescriptionService;
import org.category.apply.to.JobdescriptionTo;
import org.category.apply.util.JsonUtil;
import org.category.apply.util.exception.NotFoundException;
import org.category.apply.util.mapper.JobdescriptionMapper;
import org.category.apply.web.controller.JobdescriptionRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.jobdescription.apply.JobdescriptionTestData.*;
import static org.jobdescription.apply.CategoryTestData.FIRST_CATEGORY;
import static org.jobdescription.apply.CategoryTestData.CATEGORY_1_ID;
import static org.jobdescription.apply.UserTestData.ADMIN_EMAIL;
import static org.jobdescription.apply.UserTestData.USER_EMAIL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class JobdescriptionRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = JobdescriptionRestController.REST_URL + '/';

    @Autowired
    private JobdescriptionService service;
    @Autowired
    private JobdescriptionMapper mapper;

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void createWithLocation() throws Exception {
        Jobdescription newJobdescription = getNew();
        JobdescriptionTo newJobdescriptionTo = mapper.toTo(newJobdescription);
        ResultActions action = perform(
                MockMvcRequestBuilders.post(REST_URL)
                                      .contentType(MediaType.APPLICATION_JSON)
                                      .content(JsonUtil.writeValue(newJobdescriptionTo))
        ).andExpect(status().isCreated());

        JobdescriptionTo created = TestUtil.readFromJson(action, JobdescriptionTo.class);
        int newId = created.getId();
        newJobdescriptionTo.setId(newId);
        newJobdescription.setId(newId);

        JOBDESCRIPTION_TO_MATCHER.assertMatch(created, newJobdescriptionTo);
        JOBDESCRIPTION_MATCHER.assertMatch(service.get(newId), newJobdescription);
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + JOBDESCRIPTION_1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(JOBDESCRIPTION_TO_MATCHER.contentJson(mapper.toTo(JOBDESCRIPTION_1)));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + JOBDESCRIPTION_1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

   
   
}