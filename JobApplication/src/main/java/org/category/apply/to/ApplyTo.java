package org.category.apply.to;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

import static org.category.apply.model.AbstractBaseEntity.START_SEQ;

public class ApplyTo extends BaseTo {
    @NotNull
    @Min(START_SEQ)
    private int categoryId;
    private LocalDate appliedsDate;
    private int userId;

    public ApplyTo() {
    }

    public ApplyTo(Integer id, LocalDate appliedsDate, int userId, int categoryId) {
        super(id);
        this.appliedsDate = appliedsDate;
        this.userId = userId;
        this.categoryId = categoryId;
    }

    public LocalDate getAppliedsDate() {
        return appliedsDate;
    }

    public void setAppliedsDate(LocalDate appliedsDate) {
        this.appliedsDate = appliedsDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplyTo applyTo = (ApplyTo) o;
        return userId == applyTo.userId &&
                categoryId == applyTo.categoryId &&
                Objects.equals(id, applyTo.id) &&
                Objects.equals(appliedsDate, applyTo.appliedsDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appliedsDate, userId, categoryId);
    }

    @Override
    public String toString() {
        return "ApplyTo{" +
                "id=" + id +
                ", appliedsDate='" + appliedsDate +
                ", userId='" + userId +
                ", categoryId='" + categoryId +
                '}';
    }
}
