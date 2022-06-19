package org.jobdescription.apply;

import org.category.apply.model.Apply;
import org.category.apply.to.ApplyTo;

import static java.time.LocalDate.now;
import static org.jobdescription.apply.CategoryTestData.FIRST_CATEGORY;
import static org.jobdescription.apply.CategoryTestData.SECOND_CATEGORY;
import static org.jobdescription.apply.UserTestData.ADMIN;
import static org.jobdescription.apply.UserTestData.USER;
import static org.category.apply.model.AbstractBaseEntity.START_SEQ;

public class ApplyTestData {
    public static final TestMatcher<Apply> APPLY_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Apply.class, "user", "category");
    public static final TestMatcher<ApplyTo> APPLY_TO_MATCHER = TestMatcher.usingEqualsComparator(ApplyTo.class);

    public static final int APPLY_1_ID = START_SEQ + 12;
    public static final int APPLY_2_ID = APPLY_1_ID + 1;
    public static final int APPLY_3_ID = APPLY_1_ID + 2;

    public static final Apply APPLY_1 = new Apply(APPLY_1_ID, ADMIN, SECOND_CATEGORY, now().minusDays(1));
    public static final Apply APPLY_2 = new Apply(APPLY_2_ID, USER, SECOND_CATEGORY, now().minusDays(1));
    public static final Apply APPLY_3 = new Apply(APPLY_3_ID, ADMIN, SECOND_CATEGORY, now());

    public static Apply getNew() {
        return new Apply(null, USER, FIRST_CATEGORY, now());
    }

    public static Apply getUpdated() {
        return new Apply(APPLY_3_ID, ADMIN, FIRST_CATEGORY, now());
    }
}
