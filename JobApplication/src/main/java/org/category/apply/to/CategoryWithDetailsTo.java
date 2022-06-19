package org.category.apply.to;

import org.category.apply.model.Jobdescription;
import org.springframework.lang.Nullable;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Objects;

public class CategoryWithDetailsTo extends CategoryTo {
    private List<Jobdescription> details;

    public CategoryWithDetailsTo() {
    }

    @ConstructorProperties({"id", "name", "address", "details"})
    public CategoryWithDetailsTo(Integer id, String name, @Nullable String address, List<Jobdescription> details) {
        super(id, name, address);
        this.details = details;
    }

    public List<Jobdescription> getDetails() {
        return details;
    }

    public void setDetails(List<Jobdescription> details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryWithDetailsTo restTo = (CategoryWithDetailsTo) o;
        return Objects.equals(id, restTo.id) &&
                Objects.equals(name, restTo.name) &&
                Objects.equals(details, restTo.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, details);
    }

    @Override
    public String toString() {
        return "CategoryTo{" +
                "id=" + id +
                ", name='" + name +
                ", details='" + details +
                '}';
    }
}
