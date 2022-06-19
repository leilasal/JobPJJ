package org.category.apply.to;


import org.category.apply.util.validation.SafeHtml;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.util.Objects;

public class CategoryTo extends BaseTo {
    @NotBlank
    @Size(min = 2, max = 100)
    @SafeHtml
    protected String name;

    @Size(max = 1024)
    @SafeHtml
    @Nullable
    private String address;

    public CategoryTo() {
    }

    @ConstructorProperties({"id", "name", "address"})
    public CategoryTo(Integer id, String name, @Nullable String address) {
        super(id);
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryTo mealTo = (CategoryTo) o;
        return Objects.equals(id, mealTo.id) &&
                Objects.equals(name, mealTo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "CategoryTo{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }
}
