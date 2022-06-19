package org.category.apply.to;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.Objects;

public class JobdescriptionWithCategoryTo extends JobdescriptionTo {
    private String categoryName;

    public JobdescriptionWithCategoryTo() {
    }

    @ConstructorProperties({"id", "name", "categoryId", "categoryName", "location", "date"})
    public JobdescriptionWithCategoryTo(Integer id, String name, int categoryId, String categoryName, int location, LocalDate date) {
        super(id, categoryId, name, location, date);
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobdescriptionWithCategoryTo jobdescriptionWithCategoryTo = (JobdescriptionWithCategoryTo) o;
        return location == jobdescriptionWithCategoryTo.location &&
                categoryId == jobdescriptionWithCategoryTo.categoryId &&
                Objects.equals(id, jobdescriptionWithCategoryTo.id) &&
                Objects.equals(date, jobdescriptionWithCategoryTo.date) &&
                Objects.equals(name, jobdescriptionWithCategoryTo.name) &&
                Objects.equals(categoryName, jobdescriptionWithCategoryTo.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, categoryId, categoryName, location, date);
    }

    @Override
    public String toString() {
        return "JobdescriptionWithCategoryTo{" +
                "id=" + id +
                ", name='" + name +
                ", categoryId='" + categoryId +
                ", categoryName='" + categoryName +
                ", location=" + location +
                ", date=" + date +
                '}';
    }
}
