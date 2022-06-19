package org.category.apply.service;

import org.category.apply.model.Jobdescription;
import org.category.apply.repository.CrudJobdescriptionRepository;
import org.category.apply.repository.CrudCategoryRepository;
import org.category.apply.to.JobdescriptionTo;
import org.category.apply.util.mapper.JobdescriptionMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static org.category.apply.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
public class JobdescriptionService {

    private final CrudJobdescriptionRepository crudJobdescriptionRepository;
    private final CrudCategoryRepository crudCategoryRepository;
    private final JobdescriptionMapper mapper;

    public JobdescriptionService(CrudJobdescriptionRepository crudJobdescriptionRepository, CrudCategoryRepository crudCategoryRepository, JobdescriptionMapper mapper) {
        this.crudJobdescriptionRepository = crudJobdescriptionRepository;
        this.crudCategoryRepository = crudCategoryRepository;
        this.mapper = mapper;
    }

    @CacheEvict(value = "jobdescriptions", allEntries = true)
    @Transactional
    public Jobdescription create(JobdescriptionTo jobdescriptionTo) {
        Assert.notNull(jobdescriptionTo, "jobdescription must be not null");
        Jobdescription jobdescription = mapper.toEntity(jobdescriptionTo);
        return save(jobdescription, jobdescriptionTo.getCategoryId());
    }

    public Jobdescription save(Jobdescription jobdescription, int categoryId) {
        if (!jobdescription.isNew() && get(jobdescription.getId()) == null) {
            return null;
        }
        jobdescription.setDate(LocalDate.now());
        jobdescription.setCategory(checkNotFoundWithId(crudCategoryRepository.findById(categoryId), categoryId));
        return crudJobdescriptionRepository.save(jobdescription);
    }

    public Jobdescription get(int id) {
        return checkNotFoundWithId(crudJobdescriptionRepository.findById(id), id);
    }

    public List<Jobdescription> getAllByCategory(int categoryId) {
        return crudJobdescriptionRepository.getAllByCategory(categoryId);
    }

    @Cacheable(value = "jobdescriptions", key = "T(java.lang.String).valueOf(\"all\".toCharArray())")
    public List<Jobdescription> getAllByCategoryAndDate(int categoryId, LocalDate date) {
        Assert.notNull(date, "Date must be not null");
        return crudJobdescriptionRepository.getAllByCategoryAndDate(categoryId, date);
    }

    @CacheEvict(value = "jobdescriptions", allEntries = true)
    @Transactional
    public void update(JobdescriptionTo jobdescriptionTo) {
        Assert.notNull(jobdescriptionTo, "Jobdescription must be not null");
        Jobdescription jobdescription = mapper.toEntity(jobdescriptionTo);
        checkNotFoundWithId(save(jobdescription, jobdescriptionTo.getCategoryId()), jobdescription.id());
    }

    @CacheEvict(value = "jobdescriptions", allEntries = true)
    public void delete(int id) {
        crudJobdescriptionRepository.deleteExisted(id);
    }
}
