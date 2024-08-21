package com.laofan.iantha.web.rest;

import com.laofan.iantha.repository.BabySpecRepository;
import com.laofan.iantha.service.BabySpecService;
import com.laofan.iantha.service.dto.BabySpecDTO;
import com.laofan.iantha.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.laofan.iantha.domain.BabySpec}.
 */
@RestController
@RequestMapping("/api/baby-specs")
public class BabySpecResource {

    private static final Logger log = LoggerFactory.getLogger(BabySpecResource.class);

    private static final String ENTITY_NAME = "babySpec";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BabySpecService babySpecService;

    private final BabySpecRepository babySpecRepository;

    public BabySpecResource(BabySpecService babySpecService, BabySpecRepository babySpecRepository) {
        this.babySpecService = babySpecService;
        this.babySpecRepository = babySpecRepository;
    }

    /**
     * {@code POST  /baby-specs} : Create a new babySpec.
     *
     * @param babySpecDTO the babySpecDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new babySpecDTO, or with status {@code 400 (Bad Request)} if the babySpec has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BabySpecDTO> createBabySpec(@Valid @RequestBody BabySpecDTO babySpecDTO) throws URISyntaxException {
        log.debug("REST request to save BabySpec : {}", babySpecDTO);
        if (babySpecDTO.getId() != null) {
            throw new BadRequestAlertException("A new babySpec cannot already have an ID", ENTITY_NAME, "idexists");
        }
        babySpecDTO = babySpecService.save(babySpecDTO);
        return ResponseEntity.created(new URI("/api/baby-specs/" + babySpecDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, babySpecDTO.getId().toString()))
            .body(babySpecDTO);
    }

    /**
     * {@code PUT  /baby-specs/:id} : Updates an existing babySpec.
     *
     * @param id the id of the babySpecDTO to save.
     * @param babySpecDTO the babySpecDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated babySpecDTO,
     * or with status {@code 400 (Bad Request)} if the babySpecDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the babySpecDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BabySpecDTO> updateBabySpec(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BabySpecDTO babySpecDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BabySpec : {}, {}", id, babySpecDTO);
        if (babySpecDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, babySpecDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!babySpecRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        babySpecDTO = babySpecService.update(babySpecDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, babySpecDTO.getId().toString()))
            .body(babySpecDTO);
    }

    /**
     * {@code PATCH  /baby-specs/:id} : Partial updates given fields of an existing babySpec, field will ignore if it is null
     *
     * @param id the id of the babySpecDTO to save.
     * @param babySpecDTO the babySpecDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated babySpecDTO,
     * or with status {@code 400 (Bad Request)} if the babySpecDTO is not valid,
     * or with status {@code 404 (Not Found)} if the babySpecDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the babySpecDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BabySpecDTO> partialUpdateBabySpec(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BabySpecDTO babySpecDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BabySpec partially : {}, {}", id, babySpecDTO);
        if (babySpecDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, babySpecDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!babySpecRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BabySpecDTO> result = babySpecService.partialUpdate(babySpecDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, babySpecDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /baby-specs} : get all the babySpecs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of babySpecs in body.
     */
    @GetMapping("")
    public List<BabySpecDTO> getAllBabySpecs() {
        log.debug("REST request to get all BabySpecs");
        return babySpecService.findAll();
    }

    /**
     * {@code GET  /baby-specs/:id} : get the "id" babySpec.
     *
     * @param id the id of the babySpecDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the babySpecDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BabySpecDTO> getBabySpec(@PathVariable("id") Long id) {
        log.debug("REST request to get BabySpec : {}", id);
        Optional<BabySpecDTO> babySpecDTO = babySpecService.findOne(id);
        return ResponseUtil.wrapOrNotFound(babySpecDTO);
    }

    /**
     * {@code DELETE  /baby-specs/:id} : delete the "id" babySpec.
     *
     * @param id the id of the babySpecDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBabySpec(@PathVariable("id") Long id) {
        log.debug("REST request to delete BabySpec : {}", id);
        babySpecService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
