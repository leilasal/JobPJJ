package org.jobdescription.apply;

import org.category.apply.model.Jobdescription;
import org.category.apply.to.JobdescriptionTo;
import org.category.apply.to.JobdescriptionWithCategoryTo;

import java.util.List;

import static java.time.LocalDate.now;
import static org.jobdescription.apply.CategoryTestData.FIRST_CATEGORY;
import static org.jobdescription.apply.CategoryTestData.SECOND_CATEGORY;
import static org.category.apply.model.AbstractBaseEntity.START_SEQ;

public class JobdescriptionTestData {
    public static final TestMatcher<Jobdescription> JOBDESCRIPTION_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Jobdescription.class, "category");
    public static final TestMatcher<JobdescriptionTo> JOBDESCRIPTION_TO_MATCHER = TestMatcher.usingEqualsComparator(JobdescriptionTo.class);
    public static final TestMatcher<JobdescriptionWithCategoryTo> JOBDESCRIPTION_RESTA_TO_MATCHER = TestMatcher.usingEqualsComparator(JobdescriptionWithCategoryTo.class);

    public static final int NOT_FOUND = 100;
    public static final int JOBDESCRIPTION_1_ID = START_SEQ + 4;

    public static final Jobdescription JOBDESCRIPTION_1 = new Jobdescription(JOBDESCRIPTION_1_ID, "Java Developer", FIRST_CATEGORY, 20, now().minusDays(1));
    public static final Jobdescription JOBDESCRIPTION_2 = new Jobdescription(JOBDESCRIPTION_1_ID + 1, "Frontend developer", FIRST_CATEGORY, 40, now().minusDays(1));
   

    public static final List<Jobdescription> ALL_JOBDESCRIPTIONES = List.of(JOBDESCRIPTION_1, JOBDESCRIPTION_2);

    public static final List<Jobdescription> FIRST_CATEGORY_JOBDESCRIPTIONES = List.of(JOBDESCRIPTION_1, JOBDESCRIPTION_2);


    public static Jobdescription getNew() {
        return new Jobdescription(null, "Sandwich", FIRST_CATEGORY, 6, now());
    }

    public static Jobdescription getUpdated() {
        return new Jobdescription(JOBDESCRIPTION_1_ID, "Miso soup", FIRST_CATEGORY, 7, now());
    }
}
