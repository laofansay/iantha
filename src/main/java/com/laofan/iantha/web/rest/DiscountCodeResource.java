package com.laofan.iantha.web.rest;

import com.laofan.iantha.domain.DiscountCode;
import com.laofan.iantha.repository.DiscountCodeRepository;
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
 * REST controller for managing {@link com.laofan.iantha.domain.DiscountCode}.
 */
@RestController
@RequestMapping("/api/discount-codes")
@Transactional
public class DiscountCodeResource {

    private static final Logger log = LoggerFactory.getLogger(DiscountCodeResource.class);

    private static final String ENTITY_NAME = "discountCode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiscountCodeRepository discountCodeRepository;

    public DiscountCodeResource(DiscountCodeRepository discountCodeRepository) {
        this.discountCodeRepository = discountCodeRepository;
    }

    /**
     * {@code POST  /discount-codes} : Create a new discountCode.
     *
     * @param discountCode the discountCode to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new discountCode, or with status {@code 400 (Bad Request)} if the discountCode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DiscountCode> createDiscountCode(@Valid @RequestBody DiscountCode discountCode) throws URISyntaxException {
        log.debug("REST request to save DiscountCode : {}", discountCode);
        if (discountCode.getId() != null) {
            throw new BadRequestAlertException("A new discountCode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        discountCode = discountCodeRepository.save(discountCode);
        return ResponseEntity.created(new URI("/api/discount-codes/" + discountCode.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, discountCode.getId().toString()))
            .body(discountCode);
    }

    /**
     * {@code PUT  /discount-codes/:id} : Updates an existing discountCode.
     *
     * @param id the id of the discountCode to save.
     * @param discountCode the discountCode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated discountCode,
     * or with status {@code 400 (Bad Request)} if the discountCode is not valid,
     * or with status {@code 500 (Internal Server Error)} if the discountCode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DiscountCode> updateDiscountCode(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DiscountCode discountCode
    ) throws URISyntaxException {
        log.debug("REST request to update DiscountCode : {}, {}", id, discountCode);
        if (discountCode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, discountCode.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!discountCodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        discountCode = discountCodeRepository.save(discountCode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, discountCode.getId().toString()))
            .body(discountCode);
    }

    /**
     * {@code PATCH  /discount-codes/:id} : Partial updates given fields of an existing discountCode, field will ignore if it is null
     *
     * @param id the id of the discountCode to save.
     * @param discountCode the discountCode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated discountCode,
     * or with status {@code 400 (Bad Request)} if the discountCode is not valid,
     * or with status {@code 404 (Not Found)} if the discountCode is not found,
     * or with status {@code 500 (Internal Server Error)} if the discountCode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DiscountCode> partialUpdateDiscountCode(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DiscountCode discountCode
    ) throws URISyntaxException {
        log.debug("REST request to partial update DiscountCode partially : {}, {}", id, discountCode);
        if (discountCode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, discountCode.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!discountCodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DiscountCode> result = discountCodeRepository
            .findById(discountCode.getId())
            .map(existingDiscountCode -> {
                if (discountCode.getCode() != null) {
                    existingDiscountCode.setCode(discountCode.getCode());
                }
                if (discountCode.getStock() != null) {
                    existingDiscountCode.setStock(discountCode.getStock());
                }
                if (discountCode.getDescription() != null) {
                    existingDiscountCode.setDescription(discountCode.getDescription());
                }
                if (discountCode.getPercent() != null) {
                    existingDiscountCode.setPercent(discountCode.getPercent());
                }
                if (discountCode.getMaxDiscountAmount() != null) {
                    existingDiscountCode.setMaxDiscountAmount(discountCode.getMaxDiscountAmount());
                }
                if (discountCode.getStartDate() != null) {
                    existingDiscountCode.setStartDate(discountCode.getStartDate());
                }
                if (discountCode.getEndDate() != null) {
                    existingDiscountCode.setEndDate(discountCode.getEndDate());
                }
                if (discountCode.getCreatedAt() != null) {
                    existingDiscountCode.setCreatedAt(discountCode.getCreatedAt());
                }

                return existingDiscountCode;
            })
            .map(discountCodeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, discountCode.getId().toString())
        );
    }

    /**
     * {@code GET  /discount-codes} : get all the discountCodes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of discountCodes in body.
     */
    @GetMapping("")
    public List<DiscountCode> getAllDiscountCodes() {
        log.debug("REST request to get all DiscountCodes");
        return discountCodeRepository.findAll();
    }

    /**
     * {@code GET  /discount-codes/:id} : get the "id" discountCode.
     *
     * @param id the id of the discountCode to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the discountCode, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DiscountCode> getDiscountCode(@PathVariable("id") Long id) {
        log.debug("REST request to get DiscountCode : {}", id);
        Optional<DiscountCode> discountCode = discountCodeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(discountCode);
    }

    /**
     * {@code DELETE  /discount-codes/:id} : delete the "id" discountCode.
     *
     * @param id the id of the discountCode to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscountCode(@PathVariable("id") Long id) {
        log.debug("REST request to delete DiscountCode : {}", id);
        discountCodeRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
