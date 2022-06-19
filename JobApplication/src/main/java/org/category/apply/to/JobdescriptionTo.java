package org.category.apply.to;

import org.category.apply.util.validation.SafeHtml;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.Objects;

public class JobdescriptionTo extends BaseTo {
    protected int categoryId;

    @Size(min = 2, max = 100)
    @SafeHtml
    protected String name;

    @Positive
    protected int location;

    protected LocalDate date;

    public JobdescriptionTo() {
    }

    @ConstructorProperties({"id", "categoryId", "name", "location", "date"})
    public JobdescriptionTo(Integer id, int categoryId, String name, int location, LocalDate date) {
        super(id);
        this.categoryId = categoryId;
        this.name = name;
        this.location = location;
        this.date = date;
    }

    public int getCategoryId() { return categoryId; }

    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobdescriptionTo jobdescriptionTo = (JobdescriptionTo) o;
        return location == jobdescriptionTo.location &&
                categoryId == jobdescriptionTo.categoryId &&
                Objects.equals(id, jobdescriptionTo.id) &&
                Objects.equals(date, jobdescriptionTo.date) &&
                Objects.equals(name, jobdescriptionTo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryId, name, location, date);
    }

    @Override
    public String toString() {
        return "JobdescriptionTo{" +
                "id=" + id +
                ", categoryId='" + categoryId +
                ", name='" + name +
                ", location=" + location +
                ", date=" + date +
                '}';
    }
}
