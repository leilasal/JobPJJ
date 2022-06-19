package org.jobdescription.apply.service;

import org.junit.jupiter.api.Test;
import org.category.apply.model.Apply;
import org.category.apply.repository.CrudApplyRepository;
import org.category.apply.service.ApplyService;
import org.category.apply.util.exception.AppliedsTimeOverException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.jobdescription.apply.UserTestData.ADMIN_ID;
import static org.jobdescription.apply.UserTestData.USER_ID;
import static org.jobdescription.apply.ApplyTestData.*;

class ApplyServiceTest extends AbstractServiceTest {
    @Autowired
    private ApplyService service;
    @Autowired
    private CrudApplyRepository repository;


    @Test
    void get() {
        List<Apply> actual = service.getAllByUserId(ADMIN_ID);
        APPLY_MATCHER.assertMatch(actual, APPLY_1, APPLY_3);
    }




    @Test
    void create() {
        Apply newApply = getNew();
        Apply created = service.create(newApply.getCategory().getId(), USER_ID);
        int newId = created.id();
        newApply.setId(newId);
        APPLY_MATCHER.assertMatch(created, newApply);
        APPLY_MATCHER.assertMatch(repository.findById(newId).get(), newApply);
    }
}