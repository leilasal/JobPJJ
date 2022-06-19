package org.category.apply.repository;

import org.category.apply.model.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CrudApplyRepository extends JpaRepository<Apply, Integer> {

    @Query("SELECT v FROM Apply v WHERE v.user.id=:userId")
    List<Apply> getByUserId(@Param("userId")  int userId);

    @Query("SELECT v FROM Apply v WHERE v.user.id=:userId AND v.appliedsDate=:appliedsDate")
    Optional<Apply> getByUserIdAndDate(@Param("appliedsDate") LocalDate now, @Param("userId")  int userId);
}
