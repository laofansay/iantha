package com.laofan.iantha.web.rest;

import com.laofan.iantha.domain.PaymentProvider;
import com.laofan.iantha.repository.PaymentProviderRepository;
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
 * REST controller for managing {@link com.laofan.iantha.domain.PaymentProvider}.
 */
@RestController
@RequestMapping("/api/payment-providers")
@Transactional
public class PaymentProviderResource {

    private static final Logger log = LoggerFactory.getLogger(PaymentProviderResource.class);

    private static final String ENTITY_NAME = "paymentProvider";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentProviderRepository paymentProviderRepository;

    public PaymentProviderResource(PaymentProviderRepository paymentProviderRepository) {
        this.paymentProviderRepository = paymentProviderRepository;
    }

    /**
     * {@code POST  /payment-providers} : Create a new paymentProvider.
     *
     * @param paymentProvider the paymentProvider to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentProvider, or with status {@code 400 (Bad Request)} if the paymentProvider has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PaymentProvider> createPaymentProvider(@Valid @RequestBody PaymentProvider paymentProvider)
        throws URISyntaxException {
        log.debug("REST request to save PaymentProvider : {}", paymentProvider);
        if (paymentProvider.getId() != null) {
            throw new BadRequestAlertException("A new paymentProvider cannot already have an ID", ENTITY_NAME, "idexists");
        }
        paymentProvider = paymentProviderRepository.save(paymentProvider);
        return ResponseEntity.created(new URI("/api/payment-providers/" + paymentProvider.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, paymentProvider.getId().toString()))
            .body(paymentProvider);
    }

    /**
     * {@code PUT  /payment-providers/:id} : Updates an existing paymentProvider.
     *
     * @param id the id of the paymentProvider to save.
     * @param paymentProvider the paymentProvider to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentProvider,
     * or with status {@code 400 (Bad Request)} if the paymentProvider is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentProvider couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PaymentProvider> updatePaymentProvider(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PaymentProvider paymentProvider
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentProvider : {}, {}", id, paymentProvider);
        if (paymentProvider.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentProvider.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentProviderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        paymentProvider = paymentProviderRepository.save(paymentProvider);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentProvider.getId().toString()))
            .body(paymentProvider);
    }

    /**
     * {@code PATCH  /payment-providers/:id} : Partial updates given fields of an existing paymentProvider, field will ignore if it is null
     *
     * @param id the id of the paymentProvider to save.
     * @param paymentProvider the paymentProvider to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentProvider,
     * or with status {@code 400 (Bad Request)} if the paymentProvider is not valid,
     * or with status {@code 404 (Not Found)} if the paymentProvider is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentProvider couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentProvider> partialUpdatePaymentProvider(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PaymentProvider paymentProvider
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentProvider partially : {}, {}", id, paymentProvider);
        if (paymentProvider.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentProvider.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentProviderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentProvider> result = paymentProviderRepository
            .findById(paymentProvider.getId())
            .map(existingPaymentProvider -> {
                if (paymentProvider.getTitle() != null) {
                    existingPaymentProvider.setTitle(paymentProvider.getTitle());
                }
                if (paymentProvider.getDescription() != null) {
                    existingPaymentProvider.setDescription(paymentProvider.getDescription());
                }
                if (paymentProvider.getWebsiteUrl() != null) {
                    existingPaymentProvider.setWebsiteUrl(paymentProvider.getWebsiteUrl());
                }
                if (paymentProvider.getIsActive() != null) {
                    existingPaymentProvider.setIsActive(paymentProvider.getIsActive());
                }

                return existingPaymentProvider;
            })
            .map(paymentProviderRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentProvider.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-providers} : get all the paymentProviders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentProviders in body.
     */
    @GetMapping("")
    public List<PaymentProvider> getAllPaymentProviders() {
        log.debug("REST request to get all PaymentProviders");
        return paymentProviderRepository.findAll();
    }

    /**
     * {@code GET  /payment-providers/:id} : get the "id" paymentProvider.
     *
     * @param id the id of the paymentProvider to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentProvider, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PaymentProvider> getPaymentProvider(@PathVariable("id") Long id) {
        log.debug("REST request to get PaymentProvider : {}", id);
        Optional<PaymentProvider> paymentProvider = paymentProviderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paymentProvider);
    }

    /**
     * {@code DELETE  /payment-providers/:id} : delete the "id" paymentProvider.
     *
     * @param id the id of the paymentProvider to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentProvider(@PathVariable("id") Long id) {
        log.debug("REST request to delete PaymentProvider : {}", id);
        paymentProviderRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
