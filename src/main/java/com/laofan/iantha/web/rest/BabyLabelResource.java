package com.laofan.iantha.web.rest;

import com.laofan.iantha.repository.BabyLabelRepository;
import com.laofan.iantha.service.BabyLabelService;
import com.laofan.iantha.service.dto.BabyLabelDTO;
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
 * REST controller for managing {@link com.laofan.iantha.domain.BabyLabel}.
 */
@RestController
@RequestMapping("/api/baby-labels")
public class BabyLabelResource {

    private static final Logger log = LoggerFactory.getLogger(BabyLabelResource.class);

    private static final String ENTITY_NAME = "babyLabel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BabyLabelService babyLabelService;

    private final BabyLabelRepository babyLabelRepository;

    public BabyLabelResource(BabyLabelService babyLabelService, BabyLabelRepository babyLabelRepository) {
        this.babyLabelService = babyLabelService;
        this.babyLabelRepository = babyLabelRepository;
    }

    /**
     * {@code POST  /baby-labels} : Create a new babyLabel.
     *
     * @param babyLabelDTO the babyLabelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new babyLabelDTO, or with status {@code 400 (Bad Request)} if the babyLabel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BabyLabelDTO> createBabyLabel(@Valid @RequestBody BabyLabelDTO babyLabelDTO) throws URISyntaxException {
        log.debug("REST request to save BabyLabel : {}", babyLabelDTO);
        if (babyLabelDTO.getId() != null) {
            throw new BadRequestAlertException("A new babyLabel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        babyLabelDTO = babyLabelService.save(babyLabelDTO);
        return ResponseEntity.created(new URI("/api/baby-labels/" + babyLabelDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, babyLabelDTO.getId().toString()))
            .body(babyLabelDTO);
    }

    /**
     * {@code PUT  /baby-labels/:id} : Updates an existing babyLabel.
     *
     * @param id the id of the babyLabelDTO to save.
     * @param babyLabelDTO the babyLabelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated babyLabelDTO,
     * or with status {@code 400 (Bad Request)} if the babyLabelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the babyLabelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BabyLabelDTO> updateBabyLabel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BabyLabelDTO babyLabelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BabyLabel : {}, {}", id, babyLabelDTO);
        if (babyLabelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, babyLabelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!babyLabelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        babyLabelDTO = babyLabelService.update(babyLabelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, babyLabelDTO.getId().toString()))
            .body(babyLabelDTO);
    }

    /**
     * {@code PATCH  /baby-labels/:id} : Partial updates given fields of an existing babyLabel, field will ignore if it is null
     *
     * @param id the id of the babyLabelDTO to save.
     * @param babyLabelDTO the babyLabelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated babyLabelDTO,
     * or with status {@code 400 (Bad Request)} if the babyLabelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the babyLabelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the babyLabelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BabyLabelDTO> partialUpdateBabyLabel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BabyLabelDTO babyLabelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BabyLabel partially : {}, {}", id, babyLabelDTO);
        if (babyLabelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, babyLabelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!babyLabelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BabyLabelDTO> result = babyLabelService.partialUpdate(babyLabelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, babyLabelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /baby-labels} : get all the babyLabels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of babyLabels in body.
     */
    @GetMapping("")
    public List<BabyLabelDTO> getAllBabyLabels() {
        log.debug("REST request to get all BabyLabels");
        return babyLabelService.findAll();
    }

    /**
     * {@code GET  /baby-labels/:id} : get the "id" babyLabel.
     *
     * @param id the id of the babyLabelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the babyLabelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BabyLabelDTO> getBabyLabel(@PathVariable("id") Long id) {
        log.debug("REST request to get BabyLabel : {}", id);
        Optional<BabyLabelDTO> babyLabelDTO = babyLabelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(babyLabelDTO);
    }

    /**
     * {@code DELETE  /baby-labels/:id} : delete the "id" babyLabel.
     *
     * @param id the id of the babyLabelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBabyLabel(@PathVariable("id") Long id) {
        log.debug("REST request to delete BabyLabel : {}", id);
        babyLabelService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
