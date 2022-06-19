package org.category.apply.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.category.apply.util.validation.SafeHtml;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "address"}, name = "categorys_unique_idx"))
public class Category extends AbstractNamedEntity {

    @Column(name = "address")
    @Size(max = 1024)
    @SafeHtml
    @Nullable
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
   // @ApppliedBy("date DESC")
    @JsonManagedReference
    private List<Jobdescription> details;

    public Category() {
    }

    public Category(Integer id, String name, @Nullable String address) {
        super(id, name);
        this.address = address;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }

    public List<Jobdescription> getDetails() {
        return details;
    }

    public void setDetails(List<Jobdescription> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", details=" + details +
                '}';
    }
}
