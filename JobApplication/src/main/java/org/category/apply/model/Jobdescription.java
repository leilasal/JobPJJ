package org.category.apply.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "jobdescriptions", uniqueConstraints = @UniqueConstraint(columnNames = {"category_id", "date", "name"}, name = "jobdescriptiones_unique_idx"))
public class Jobdescription extends AbstractNamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @Nullable
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Category category;

    @Column(name = "location", nullable = false)
    @Range(min = 1)
    private int location;

    @Column(name = "date", nullable = false, columnDefinition = "DATE DEFAULT now()")
    @NotNull
    private LocalDate date;

    public Jobdescription() {
    }

    public Jobdescription(Integer id, String name, Category category, int location, LocalDate date) {
        super(id, name);
        this.category = category;
        this.location = location;
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
    public String toString() {
        return "Jobdescription{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location=" + location +
                ", date=" + date +
                '}';
    }
}
