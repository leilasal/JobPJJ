package org.category.apply.web.controller;

import org.category.apply.model.Category;
import org.category.apply.service.CategoryService;
import org.category.apply.to.CategoryTo;
import org.category.apply.to.CategoryWithDetailsTo;
import org.category.apply.util.mapper.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.category.apply.util.validation.ValidationUtil.assureIdConsistent;
import static org.category.apply.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = CategoryRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryRestController {
    public static final String REST_URL = "/rest/categories";
    private static final Logger log = LoggerFactory.getLogger(JobdescriptionRestController.class);

    private final CategoryService service;
    private final CategoryMapper mapper;

    public CategoryRestController(CategoryService service, CategoryMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryTo> createWithLocation(@Valid @RequestBody CategoryTo categoryTo) {
        checkNew(categoryTo);
        log.info("Create {}", categoryTo);
        Category created = service.create(categoryTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequestUri()
                                                          .path(REST_URL + "/{id}")
                                                          .buildAndExpand(created.getId())
                                                          .toUri();
        return ResponseEntity.created(uriOfNewResource)
                             .body(mapper.toTo(created));
    }

    @GetMapping("/{id}")
    public CategoryTo get(@PathVariable int id) {
        log.info("Get category {}", id);
        return mapper.toTo(service.get(id));
    }

    @GetMapping("/{id}/today")
    public CategoryWithDetailsTo getForToday(@PathVariable int id) {
        log.info("Get category {} with details", id);
        return mapper.toToWithDetails(service.getWithTodayDetails(id));
    }

    @GetMapping
    public List<CategoryTo> getAll() {
        log.info("Get all categories");
        return mapper.getToList(service.getAll());
    }

    @GetMapping("/today")
    public List<CategoryWithDetailsTo> getAllForToday() {
        log.info("Get all categories");
        return mapper.getToWithDetailsList(service.getAllWithTodayDetails());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody CategoryTo categoryTo, @PathVariable int id) {
        assureIdConsistent(categoryTo, id);
        log.info("Update {}", categoryTo);
        service.update(categoryTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete category {}", id);
        service.delete(id);
    }
}
