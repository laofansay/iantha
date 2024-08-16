package com.laofan.iantha.web.rest;

import com.laofan.iantha.domain.BabySpec;
import com.laofan.iantha.repository.BabySpecRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.laofan.iantha.domain.BabySpec}.
 */
@RestController
@RequestMapping("/api/baby-specs")
@Transactional
public class BabySpecResource {

    private static final Logger log = LoggerFactory.getLogger(BabySpecResource.class);

    private static final String ENTITY_NAME = "babySpec";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BabySpecRepository babySpecRepository;

    public BabySpecResource(BabySpecRepository babySpecRepository) {
        this.babySpecRepository = babySpecRepository;
    }

    /**
     * {@code POST  /baby-specs} : Create a new babySpec.
     *
     * @param babySpec the babySpec to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new babySpec, or with status {@code 400 (Bad Request)} if the babySpec has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BabySpec> createBabySpec(@Valid @RequestBody BabySpec babySpec) throws URISyntaxException {
        log.debug("REST request to save BabySpec : {}", babySpec);
        if (babySpec.getId() != null) {
            throw new BadRequestAlertException("A new babySpec cannot already have an ID", ENTITY_NAME, "idexists");
        }
        babySpec = babySpecRepository.save(babySpec);
        return ResponseEntity.created(new URI("/api/baby-specs/" + babySpec.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, babySpec.getId().toString()))
            .body(babySpec);
    }

    /**
     * {@code PUT  /baby-specs/:id} : Updates an existing babySpec.
     *
     * @param id the id of the babySpec to save.
     * @param babySpec the babySpec to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated babySpec,
     * or with status {@code 400 (Bad Request)} if the babySpec is not valid,
     * or with status {@code 500 (Internal Server Error)} if the babySpec couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BabySpec> updateBabySpec(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BabySpec babySpec
    ) throws URISyntaxException {
        log.debug("REST request to update BabySpec : {}, {}", id, babySpec);
        if (babySpec.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, babySpec.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!babySpecRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        babySpec = babySpecRepository.save(babySpec);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, babySpec.getId().toString()))
            .body(babySpec);
    }

    /**
     * {@code PATCH  /baby-specs/:id} : Partial updates given fields of an existing babySpec, field will ignore if it is null
     *
     * @param id the id of the babySpec to save.
     * @param babySpec the babySpec to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated babySpec,
     * or with status {@code 400 (Bad Request)} if the babySpec is not valid,
     * or with status {@code 404 (Not Found)} if the babySpec is not found,
     * or with status {@code 500 (Internal Server Error)} if the babySpec couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BabySpec> partialUpdateBabySpec(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BabySpec babySpec
    ) throws URISyntaxException {
        log.debug("REST request to partial update BabySpec partially : {}, {}", id, babySpec);
        if (babySpec.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, babySpec.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!babySpecRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BabySpec> result = babySpecRepository
            .findById(babySpec.getId())
            .map(existingBabySpec -> {
                if (babySpec.getSpecCode() != null) {
                    existingBabySpec.setSpecCode(babySpec.getSpecCode());
                }
                if (babySpec.getSpecTitle() != null) {
                    existingBabySpec.setSpecTitle(babySpec.getSpecTitle());
                }
                if (babySpec.getDescription() != null) {
                    existingBabySpec.setDescription(babySpec.getDescription());
                }
                if (babySpec.getSpecQuantity() != null) {
                    existingBabySpec.setSpecQuantity(babySpec.getSpecQuantity());
                }
                if (babySpec.getGuidePrice() != null) {
                    existingBabySpec.setGuidePrice(babySpec.getGuidePrice());
                }
                if (babySpec.getSpecPrice() != null) {
                    existingBabySpec.setSpecPrice(babySpec.getSpecPrice());
                }
                if (babySpec.getShowPrice() != null) {
                    existingBabySpec.setShowPrice(babySpec.getShowPrice());
                }
                if (babySpec.getSpecStatus() != null) {
                    existingBabySpec.setSpecStatus(babySpec.getSpecStatus());
                }
                if (babySpec.getImages() != null) {
                    existingBabySpec.setImages(babySpec.getImages());
                }
                if (babySpec.getSellCount() != null) {
                    existingBabySpec.setSellCount(babySpec.getSellCount());
                }
                if (babySpec.getStockCount() != null) {
                    existingBabySpec.setStockCount(babySpec.getStockCount());
                }
                if (babySpec.getCreatedAt() != null) {
                    existingBabySpec.setCreatedAt(babySpec.getCreatedAt());
                }
                if (babySpec.getUpdatedAt() != null) {
                    existingBabySpec.setUpdatedAt(babySpec.getUpdatedAt());
                }

                return existingBabySpec;
            })
            .map(babySpecRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, babySpec.getId().toString())
        );
    }

    /**
     * {@code GET  /baby-specs} : get all the babySpecs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of babySpecs in body.
     */
    @GetMapping("")
    public List<BabySpec> getAllBabySpecs() {
        log.debug("REST request to get all BabySpecs");
        return babySpecRepository.findAll();
    }

    /**
     * {@code GET  /baby-specs/:id} : get the "id" babySpec.
     *
     * @param id the id of the babySpec to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the babySpec, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BabySpec> getBabySpec(@PathVariable("id") Long id) {
        log.debug("REST request to get BabySpec : {}", id);
        Optional<BabySpec> babySpec = babySpecRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(babySpec);
    }

    /**
     * {@code DELETE  /baby-specs/:id} : delete the "id" babySpec.
     *
     * @param id the id of the babySpec to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBabySpec(@PathVariable("id") Long id) {
        log.debug("REST request to delete BabySpec : {}", id);
        babySpecRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
