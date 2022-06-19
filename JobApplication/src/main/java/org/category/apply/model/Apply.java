package org.category.apply.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "applied", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "applieds_date"}, name = "applys_unique_idx"))
public class Apply extends AbstractBaseEntity {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "global_seq", foreignKeyDefinition = "START WITH 100000"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "global_seq", foreignKeyDefinition = "START WITH 100000"))
    private Category category;

    @Column(name = "applieds_date", nullable = false, columnDefinition = "DATE DEFAULT now()")
    @NotNull
    private LocalDate appliedsDate;

    public Apply() {
    }

    public Apply(Integer id, User user, Category category, LocalDate appliedsDate) {
        super(id);
        this.user = user;
        this.category = category;
        this.appliedsDate = appliedsDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getAppliedsDate() {
        return appliedsDate;
    }

    public void setAppliedsDate(LocalDate appliedsDate) {
        this.appliedsDate = appliedsDate;
    }

    @Override
    public String toString() {
        return "Apply{" +
                "id=" + id +
                ", user=" + user +
                ", category=" + category +
                ", appliedsDate=" + appliedsDate +
                '}';
    }
}
