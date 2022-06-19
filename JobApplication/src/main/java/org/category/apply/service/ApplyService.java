package org.category.apply.service;

import org.category.apply.model.Apply;
import org.category.apply.repository.CrudCategoryRepository;
import org.category.apply.repository.CrudUserRepository;
import org.category.apply.repository.CrudApplyRepository;
import org.category.apply.util.exception.DataConflictException;
import org.category.apply.util.exception.AppliedsTimeOverException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.LocalDate.now;
import static org.category.apply.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
public class ApplyService {

    @Value("${app.apply_endtime}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime applyEndTime;

    private final CrudApplyRepository crudApplyRepository;
    private final CrudUserRepository crudUserRepository;
    private final CrudCategoryRepository crudCategoryRepository;

    public ApplyService(CrudApplyRepository crudApplyRepository, CrudUserRepository crudUserRepository, CrudCategoryRepository crudCategoryRepository) {
        this.crudApplyRepository = crudApplyRepository;
        this.crudUserRepository = crudUserRepository;
        this.crudCategoryRepository = crudCategoryRepository;
    }

    @Transactional
    public Apply create(int categoryId, int userId) {
        Assert.isTrue(categoryId != 0, "Category Id must be not null");
        Apply apply = new Apply(null, null, null, LocalDate.now());
        return save(apply, categoryId, userId);
    }

    public Apply save(Apply apply, int categoryId, int userId) {
        apply.setUser(checkNotFoundWithId(crudUserRepository.findById(userId), userId));
        apply.setCategory(checkNotFoundWithId(crudCategoryRepository.findById(categoryId), categoryId));
        return crudApplyRepository.save(apply);
    }

    public List<Apply> getAllByUserId(int userId) {
        return crudApplyRepository.getByUserId(userId);
    }

    public Apply getLast(int userId) {
        return checkNotFoundWithId(crudApplyRepository.getByUserIdAndDate(now(), userId), userId);
    }

    @Transactional
    public void update(int categoryId, int userId) {
        Assert.isTrue(categoryId != 0, "Category Id must be not null");
        checkTimeOver();

        Apply apply = crudApplyRepository.getByUserIdAndDate(now(), userId)
                                      .orElseThrow(() -> new DataConflictException("Have not applyd today"));
        apply.setAppliedsDate(now());
        save(apply, categoryId, userId);
    }

    public void checkTimeOver() {
        if (isAppliedsTimeOver()) {
            throw new AppliedsTimeOverException("It's " + LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME) + ". Time for applieds is over!");
        }
    }

    public boolean isAppliedsTimeOver() {
        return LocalTime.now().isAfter(applyEndTime);
    }
}
