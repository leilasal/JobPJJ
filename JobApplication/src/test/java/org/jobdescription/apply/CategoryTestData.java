package org.jobdescription.apply;

import org.category.apply.model.Category;
import org.category.apply.to.CategoryTo;
import org.category.apply.to.CategoryWithDetailsTo;

import java.util.ArrayList;
import java.util.List;

import static org.category.apply.model.AbstractBaseEntity.START_SEQ;

public class CategoryTestData {
    public static final TestMatcher<Category> CATEGORY_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Category.class, "details");
    public static final TestMatcher<CategoryTo> CATEGORY_TO_MATCHER = TestMatcher.usingEqualsComparator(CategoryTo.class);
    public static final TestMatcher<CategoryWithDetailsTo> CATEGORY_WITH_DETAILS_MATCHER = TestMatcher.usingEqualsComparator(CategoryWithDetailsTo.class);

    public static final int NOT_FOUND = 1000;
    public static final int CATEGORY_1_ID = START_SEQ + 2;
    public static final int CATEGORY_2_ID = START_SEQ + 3;

    public static final Category FIRST_CATEGORY = new Category(CATEGORY_1_ID, "Category1", "");
    public static final Category SECOND_CATEGORY = new Category(CATEGORY_2_ID, "Category2", "");

    public static final List<Category> ALL_CATEGORYS = List.of(FIRST_CATEGORY, SECOND_CATEGORY);

    public static void initDetails() {
        FIRST_CATEGORY.setDetails(new ArrayList<>());
        SECOND_CATEGORY.setDetails(new ArrayList<>());
    }

    public static Category getNew() {
        return new Category(null, "Category3", "");
    }

    public static Category getUpdated() {
        return new Category(CATEGORY_1_ID, "", "");
    }
}
