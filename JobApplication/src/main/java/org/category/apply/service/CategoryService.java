package org.category.apply.service;

import org.category.apply.model.Category;
import org.category.apply.repository.CrudCategoryRepository;
import org.category.apply.to.CategoryTo;
import org.category.apply.util.mapper.CategoryMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static org.category.apply.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
public class CategoryService {

    private final LocalDate today = LocalDate.now();
    private final CrudCategoryRepository crudCategoryRepository;
    private final CategoryMapper mapper;

    public CategoryService(CrudCategoryRepository crudCategoryRepository, CategoryMapper mapper) {
        this.crudCategoryRepository = crudCategoryRepository;
        this.mapper = mapper;
    }

    @Transactional
    public Category create(CategoryTo categoryTo) {
        Assert.notNull(categoryTo, "Category must be not null");
        return save(categoryTo);
    }

    public Category save(CategoryTo categoryTo) {
        return crudCategoryRepository.save(mapper.toEntity(categoryTo));
    }

    public Category get(int id) {
        return checkNotFoundWithId(crudCategoryRepository.findById(id), id);
    }

    public Category getWithTodayDetails(int id) {
        return checkNotFoundWithId(crudCategoryRepository.getWithJobdescriptions(id, today), id);
    }

    public List<Category> getAll() {
        return crudCategoryRepository.findAll();
    }

    @Cacheable(value = "jobdescriptions", key = "T(java.lang.String).valueOf(\"all\".toCharArray())")
    public List<Category> getAllWithTodayDetails() {
        return crudCategoryRepository.getAllWithJobdescriptions(today);
    }

    @CacheEvict(value = "jobdescriptions", allEntries = true)
    @Transactional
    public void update(CategoryTo categoryTo) {
        Assert.notNull(categoryTo, "Category must be not null");
        checkNotFoundWithId(save(categoryTo), categoryTo.getId());
    }

    @CacheEvict(value = "jobdescriptions", allEntries = true)
    public void delete(int id) {
        crudCategoryRepository.deleteExisted(id);
    }
}
