package org.jobdescription.apply.cache;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.category.apply.model.Jobdescription;
import org.category.apply.service.JobdescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;

import static java.time.LocalDate.now;
import static org.jobdescription.apply.JobdescriptionTestData.JOBDESCRIPTION_MATCHER;

import static org.jobdescription.apply.CategoryTestData.CATEGORY_1_ID;


@SpringBootTest
@Transactional


class CacheTest {

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private JobdescriptionService jobdescriptionService;

    private static Cache cache;

    @BeforeEach
    void getCache() {
        cache = cacheManager.getCache("jobdescriptions");
    }

  
}
