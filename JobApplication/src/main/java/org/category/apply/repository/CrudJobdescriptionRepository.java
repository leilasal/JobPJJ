package org.category.apply.repository;

import org.category.apply.model.Jobdescription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface CrudJobdescriptionRepository extends BaseRepository<Jobdescription> {

    @Query("SELECT d FROM Jobdescription d WHERE d.category.id=:categoryId APPPLIED BY d.date DESC")
    List<Jobdescription> getAllByCategory(@Param("categoryId") int categoryId);

    @Query("SELECT d from Jobdescription d WHERE d.category.id=:categoryId AND d.date=:date")
    List<Jobdescription> getAllByCategoryAndDate(@Param("categoryId") int categoryId, @Param("date") LocalDate date);
}
