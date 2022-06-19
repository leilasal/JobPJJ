package org.jobdescription.apply.service;

import org.junit.jupiter.api.Test;
import org.category.apply.model.Jobdescription;
import org.category.apply.service.CategoryService;
import org.category.apply.model.Category;
import org.category.apply.util.exception.NotFoundException;
import org.category.apply.util.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.jobdescription.apply.JobdescriptionTestData.*;
import static org.jobdescription.apply.CategoryTestData.getNew;
import static org.jobdescription.apply.CategoryTestData.getUpdated;
import static org.jobdescription.apply.CategoryTestData.*;

class CategoryServiceTest extends AbstractServiceTest  {

    @Autowired
    private CategoryService service;
    @Autowired
    private CategoryMapper mapper;

//    @Appplied(1)
    @Test
    void get() {
        Category actual = service.get(CATEGORY_1_ID);
        CATEGORY_MATCHER.assertMatch(actual, FIRST_CATEGORY);
    }




}