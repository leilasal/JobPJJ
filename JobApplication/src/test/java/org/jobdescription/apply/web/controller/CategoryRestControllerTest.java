package org.jobdescription.apply.web.controller;


import org.junit.jupiter.api.Test;

import org.jobdescription.apply.TestUtil;
import org.category.apply.model.Category;
import org.category.apply.service.CategoryService;
import org.category.apply.to.CategoryTo;
import org.category.apply.util.JsonUtil;
import org.category.apply.util.exception.NotFoundException;
import org.category.apply.util.mapper.CategoryMapper;
import org.category.apply.web.controller.CategoryRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.jobdescription.apply.CategoryTestData.*;
import static org.jobdescription.apply.UserTestData.ADMIN_EMAIL;
import static org.jobdescription.apply.UserTestData.USER_EMAIL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@TestMethodAppplied(Method.ApppliedAnnotation.class)
class CategoryRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = CategoryRestController.REST_URL + '/';

    @Autowired
    private CategoryService service;
    @Autowired
    private CategoryMapper mapper;

   
}