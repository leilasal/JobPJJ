package org.jobdescription.apply.web.controller;

import org.junit.jupiter.api.Test;
import org.jobdescription.apply.TestUtil;
import org.category.apply.model.Apply;
import org.category.apply.repository.CrudApplyRepository;
import org.category.apply.service.ApplyService;
import org.category.apply.to.ApplyTo;
import org.category.apply.util.mapper.ApplyMapper;
import org.category.apply.web.controller.ApplyRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.jobdescription.apply.UserTestData.ADMIN_EMAIL;
import static org.jobdescription.apply.UserTestData.USER_EMAIL;
import static org.jobdescription.apply.ApplyTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApplyRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ApplyRestController.REST_URL + "/";

    @Autowired
    private ApplyService service;
    @Autowired
    private CrudApplyRepository repository;
    @Autowired
    private ApplyMapper mapper;

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void applyToday() throws Exception {
        Apply newApply = getNew();
        ResultActions action = perform(
                MockMvcRequestBuilders.post(REST_URL)
                                      .queryParam("categoryId", String.valueOf(newApply.getCategory().getId()))
                                      .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        ApplyTo created = TestUtil.readFromJson(action, ApplyTo.class);
        int newId = created.getId();
        newApply.setId(newId);

        APPLY_TO_MATCHER.assertMatch(created, mapper.toTo(newApply));
        APPLY_MATCHER.assertMatch(repository.findById(newId).get(), newApply);
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void getOwnApplied() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(APPLY_TO_MATCHER.contentJson(mapper.getToList(List.of(APPLY_2))));
    }

    @Test
    void getOwnAppliedUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    
   
}