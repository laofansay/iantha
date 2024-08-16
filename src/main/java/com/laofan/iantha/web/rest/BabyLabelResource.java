package com.laofan.iantha.web.rest;

import com.laofan.iantha.domain.BabyLabel;
import com.laofan.iantha.repository.BabyLabelRepository;
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
 * REST controller for managing {@link com.laofan.iantha.domain.BabyLabel}.
 */
@RestController
@RequestMapping("/api/baby-labels")
@Transactional
public class BabyLabelResource {

    private static final Logger log = LoggerFactory.getLogger(BabyLabelResource.class);

    private static final String ENTITY_NAME = "babyLabel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BabyLabelRepository babyLabelRepository;

    public BabyLabelResource(BabyLabelRepository babyLabelRepository) {
        this.babyLabelRepository = babyLabelRepository;
    }

    /**
     * {@code POST  /baby-labels} : Create a new babyLabel.
     *
     * @param babyLabel the babyLabel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new babyLabel, or with status {@code 400 (Bad Request)} if the babyLabel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BabyLabel> createBabyLabel(@Valid @RequestBody BabyLabel babyLabel) throws URISyntaxException {
        log.debug("REST request to save BabyLabel : {}", babyLabel);
        if (babyLabel.getId() != null) {
            throw new BadRequestAlertException("A new babyLabel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        babyLabel = babyLabelRepository.save(babyLabel);
        return ResponseEntity.created(new URI("/api/baby-labels/" + babyLabel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, babyLabel.getId().toString()))
            .body(babyLabel);
    }

    /**
     * {@code PUT  /baby-labels/:id} : Updates an existing babyLabel.
     *
     * @param id the id of the babyLabel to save.
     * @param babyLabel the babyLabel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated babyLabel,
     * or with status {@code 400 (Bad Request)} if the babyLabel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the babyLabel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BabyLabel> updateBabyLabel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BabyLabel babyLabel
    ) throws URISyntaxException {
        log.debug("REST request to update BabyLabel : {}, {}", id, babyLabel);
        if (babyLabel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, babyLabel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!babyLabelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        babyLabel = babyLabelRepository.save(babyLabel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, babyLabel.getId().toString()))
            .body(babyLabel);
    }

    /**
     * {@code PATCH  /baby-labels/:id} : Partial updates given fields of an existing babyLabel, field will ignore if it is null
     *
     * @param id the id of the babyLabel to save.
     * @param babyLabel the babyLabel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated babyLabel,
     * or with status {@code 400 (Bad Request)} if the babyLabel is not valid,
     * or with status {@code 404 (Not Found)} if the babyLabel is not found,
     * or with status {@code 500 (Internal Server Error)} if the babyLabel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BabyLabel> partialUpdateBabyLabel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BabyLabel babyLabel
    ) throws URISyntaxException {
        log.debug("REST request to partial update BabyLabel partially : {}, {}", id, babyLabel);
        if (babyLabel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, babyLabel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!babyLabelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BabyLabel> result = babyLabelRepository
            .findById(babyLabel.getId())
            .map(existingBabyLabel -> {
                if (babyLabel.getTitle() != null) {
                    existingBabyLabel.setTitle(babyLabel.getTitle());
                }
                if (babyLabel.getLabelCate() != null) {
                    existingBabyLabel.setLabelCate(babyLabel.getLabelCate());
                }
                if (babyLabel.getLabelCode() != null) {
                    existingBabyLabel.setLabelCode(babyLabel.getLabelCode());
                }
                if (babyLabel.getLabelAttr() != null) {
                    existingBabyLabel.setLabelAttr(babyLabel.getLabelAttr());
                }
                if (babyLabel.getIdentify() != null) {
                    existingBabyLabel.setIdentify(babyLabel.getIdentify());
                }
                if (babyLabel.getRuleReadme() != null) {
                    existingBabyLabel.setRuleReadme(babyLabel.getRuleReadme());
                }
                if (babyLabel.getRuleExpression() != null) {
                    existingBabyLabel.setRuleExpression(babyLabel.getRuleExpression());
                }

                return existingBabyLabel;
            })
            .map(babyLabelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, babyLabel.getId().toString())
        );
    }

    /**
     * {@code GET  /baby-labels} : get all the babyLabels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of babyLabels in body.
     */
    @GetMapping("")
    public List<BabyLabel> getAllBabyLabels() {
        log.debug("REST request to get all BabyLabels");
        return babyLabelRepository.findAll();
    }

    /**
     * {@code GET  /baby-labels/:id} : get the "id" babyLabel.
     *
     * @param id the id of the babyLabel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the babyLabel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BabyLabel> getBabyLabel(@PathVariable("id") Long id) {
        log.debug("REST request to get BabyLabel : {}", id);
        Optional<BabyLabel> babyLabel = babyLabelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(babyLabel);
    }

    /**
     * {@code DELETE  /baby-labels/:id} : delete the "id" babyLabel.
     *
     * @param id the id of the babyLabel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBabyLabel(@PathVariable("id") Long id) {
        log.debug("REST request to delete BabyLabel : {}", id);
        babyLabelRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
