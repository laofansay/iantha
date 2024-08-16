package com.laofan.iantha.web.rest;

import com.laofan.iantha.domain.Refund;
import com.laofan.iantha.repository.RefundRepository;
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
 * REST controller for managing {@link com.laofan.iantha.domain.Refund}.
 */
@RestController
@RequestMapping("/api/refunds")
@Transactional
public class RefundResource {

    private static final Logger log = LoggerFactory.getLogger(RefundResource.class);

    private static final String ENTITY_NAME = "refund";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RefundRepository refundRepository;

    public RefundResource(RefundRepository refundRepository) {
        this.refundRepository = refundRepository;
    }

    /**
     * {@code POST  /refunds} : Create a new refund.
     *
     * @param refund the refund to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new refund, or with status {@code 400 (Bad Request)} if the refund has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Refund> createRefund(@Valid @RequestBody Refund refund) throws URISyntaxException {
        log.debug("REST request to save Refund : {}", refund);
        if (refund.getId() != null) {
            throw new BadRequestAlertException("A new refund cannot already have an ID", ENTITY_NAME, "idexists");
        }
        refund = refundRepository.save(refund);
        return ResponseEntity.created(new URI("/api/refunds/" + refund.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, refund.getId().toString()))
            .body(refund);
    }

    /**
     * {@code PUT  /refunds/:id} : Updates an existing refund.
     *
     * @param id the id of the refund to save.
     * @param refund the refund to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated refund,
     * or with status {@code 400 (Bad Request)} if the refund is not valid,
     * or with status {@code 500 (Internal Server Error)} if the refund couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Refund> updateRefund(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Refund refund
    ) throws URISyntaxException {
        log.debug("REST request to update Refund : {}, {}", id, refund);
        if (refund.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, refund.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!refundRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        refund = refundRepository.save(refund);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, refund.getId().toString()))
            .body(refund);
    }

    /**
     * {@code PATCH  /refunds/:id} : Partial updates given fields of an existing refund, field will ignore if it is null
     *
     * @param id the id of the refund to save.
     * @param refund the refund to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated refund,
     * or with status {@code 400 (Bad Request)} if the refund is not valid,
     * or with status {@code 404 (Not Found)} if the refund is not found,
     * or with status {@code 500 (Internal Server Error)} if the refund couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Refund> partialUpdateRefund(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Refund refund
    ) throws URISyntaxException {
        log.debug("REST request to partial update Refund partially : {}, {}", id, refund);
        if (refund.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, refund.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!refundRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Refund> result = refundRepository
            .findById(refund.getId())
            .map(existingRefund -> {
                if (refund.getAmount() != null) {
                    existingRefund.setAmount(refund.getAmount());
                }
                if (refund.getReason() != null) {
                    existingRefund.setReason(refund.getReason());
                }
                if (refund.getCreatedAt() != null) {
                    existingRefund.setCreatedAt(refund.getCreatedAt());
                }
                if (refund.getUpdatedAt() != null) {
                    existingRefund.setUpdatedAt(refund.getUpdatedAt());
                }

                return existingRefund;
            })
            .map(refundRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, refund.getId().toString())
        );
    }

    /**
     * {@code GET  /refunds} : get all the refunds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of refunds in body.
     */
    @GetMapping("")
    public List<Refund> getAllRefunds() {
        log.debug("REST request to get all Refunds");
        return refundRepository.findAll();
    }

    /**
     * {@code GET  /refunds/:id} : get the "id" refund.
     *
     * @param id the id of the refund to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the refund, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Refund> getRefund(@PathVariable("id") Long id) {
        log.debug("REST request to get Refund : {}", id);
        Optional<Refund> refund = refundRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(refund);
    }

    /**
     * {@code DELETE  /refunds/:id} : delete the "id" refund.
     *
     * @param id the id of the refund to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRefund(@PathVariable("id") Long id) {
        log.debug("REST request to delete Refund : {}", id);
        refundRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
