package org.category.apply.web.controller;

import org.category.apply.model.Apply;
import org.category.apply.service.ApplyService;
import org.category.apply.to.ApplyTo;
import org.category.apply.util.SecurityUtil;
import org.category.apply.util.mapper.ApplyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = ApplyRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ApplyRestController {
    public static final String REST_URL = "/rest/applied";
    private static final Logger log = LoggerFactory.getLogger(ApplyRestController.class);

    private final ApplyService service;
    private final ApplyMapper mapper;

    public ApplyRestController(ApplyService service, ApplyMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApplyTo> applyToday(@RequestParam int categoryId) {
        int userId = SecurityUtil.authUserId();
        log.info("Apply of user {} for category {}", userId, categoryId);
        Apply created = service.create(categoryId, userId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                                                          .path(REST_URL + "/{id}")
                                                          .buildAndExpand(created.getId())
                                                          .toUri();
        return ResponseEntity.created(uriOfNewResource)
                             .body(mapper.toTo(created));
    }

    @GetMapping()
    public List<ApplyTo> getOwnApplied() {
        int userId = SecurityUtil.authUserId();
        log.info("Get apply of user {}", userId);
        return mapper.getToList(service.getAllByUserId(userId));
    }

    @GetMapping("/last")
    public ApplyTo getLastApply() {
        int userId = SecurityUtil.authUserId();
        log.info("Get last apply");
        return mapper.toTo(service.getLast(userId));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reapplyToday(@RequestParam int categoryId) {
        int userId = SecurityUtil.authUserId();
        log.info("Update apply of user {} for category {}", userId, categoryId);
        service.update(categoryId, userId);
    }
}
