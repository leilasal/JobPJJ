package org.jobdescription.apply.service;

import org.junit.jupiter.api.Test;
import org.category.apply.model.Jobdescription;
import org.category.apply.service.JobdescriptionService;
import org.category.apply.util.exception.NotFoundException;
import org.category.apply.util.mapper.JobdescriptionMapper;
import org.springframework.beans.factory.annotation.Autowired;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.jobdescription.apply.JobdescriptionTestData.*;
import static org.jobdescription.apply.CategoryTestData.CATEGORY_1_ID;

class JobdescriptionServiceTest extends AbstractServiceTest {
    @Autowired
    private JobdescriptionService service;
    @Autowired
    private JobdescriptionMapper mapper;

//    @Appplied(1)
    @Test
    void get() {
        Jobdescription actual = service.get(JOBDESCRIPTION_1_ID);
        JOBDESCRIPTION_MATCHER.assertMatch(actual, JOBDESCRIPTION_1);
    }

//    @Appplied(2)
    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }


}