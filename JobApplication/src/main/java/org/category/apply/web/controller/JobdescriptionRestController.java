package org.category.apply.web.controller;

import org.category.apply.model.Jobdescription;
import org.category.apply.service.JobdescriptionService;
import org.category.apply.to.JobdescriptionTo;
import org.category.apply.util.mapper.JobdescriptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.category.apply.util.validation.ValidationUtil.assureIdConsistent;
import static org.category.apply.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = JobdescriptionRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class JobdescriptionRestController {
    public static final String REST_URL = "/rest/jobdescriptions";
    private static final Logger log = LoggerFactory.getLogger(JobdescriptionRestController.class);

    private final JobdescriptionService service;
    private final JobdescriptionMapper mapper;

    public JobdescriptionRestController(JobdescriptionService service, JobdescriptionMapper mapper) {
        this.service = service;
         this.mapper = mapper;
     }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JobdescriptionTo> createWithLocation(@Valid @RequestBody JobdescriptionTo jobdescriptionTo) {
        checkNew(jobdescriptionTo);
        log.info("Create {}", jobdescriptionTo);
        Jobdescription created = service.create(jobdescriptionTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                                                          .path(REST_URL + "/{id}")
                                                          .buildAndExpand(created.getId())
                                                          .toUri();
        return ResponseEntity.created(uriOfNewResource)
                             .body(mapper.toTo(created));
    }

    @GetMapping("/{id}")
    public JobdescriptionTo get(@PathVariable int id) {
        log.info("Get {}", id);
        return mapper.toTo(service.get(id));
    }

    @GetMapping
    private List<JobdescriptionTo> getAllBy(@RequestParam int categoryId,
                                  @RequestParam Optional<LocalDate> date) {
        log.info("Get all jobdescriptions from category {}{}", categoryId, date.isPresent() ? " for " + date : "");
        return date.map(d -> mapper.getEntityList(service.getAllByCategoryAndDate(categoryId, d)))
                   .orElse(mapper.getEntityList(service.getAllByCategory(categoryId)));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody JobdescriptionTo jobdescriptionTo, @PathVariable int id) {
        assureIdConsistent(jobdescriptionTo, id);
        log.info("Update {}", jobdescriptionTo);
        service.update(jobdescriptionTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete {}", id);
        service.delete(id);
    }
}
