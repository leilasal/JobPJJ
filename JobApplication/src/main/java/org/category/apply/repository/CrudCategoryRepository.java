package org.category.apply.repository;

import org.category.apply.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CrudCategoryRepository extends BaseRepository<Category> {

    @Query("SELECT DISTINCT r from Category r JOIN FETCH r.details m WHERE r.id=:id AND  m.date=:date")
    Category getWithJobdescriptions(@Param("id") int id, @Param("date") LocalDate date);

    @Query("SELECT DISTINCT r from Category r JOIN FETCH r.details m WHERE m.date=?1")
    List<Category> getAllWithJobdescriptions(LocalDate date);
}
