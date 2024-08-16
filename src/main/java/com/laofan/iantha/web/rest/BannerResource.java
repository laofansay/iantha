package com.laofan.iantha.web.rest;

import com.laofan.iantha.domain.Banner;
import com.laofan.iantha.repository.BannerRepository;
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
 * REST controller for managing {@link com.laofan.iantha.domain.Banner}.
 */
@RestController
@RequestMapping("/api/banners")
@Transactional
public class BannerResource {

    private static final Logger log = LoggerFactory.getLogger(BannerResource.class);

    private static final String ENTITY_NAME = "banner";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BannerRepository bannerRepository;

    public BannerResource(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    /**
     * {@code POST  /banners} : Create a new banner.
     *
     * @param banner the banner to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new banner, or with status {@code 400 (Bad Request)} if the banner has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Banner> createBanner(@Valid @RequestBody Banner banner) throws URISyntaxException {
        log.debug("REST request to save Banner : {}", banner);
        if (banner.getId() != null) {
            throw new BadRequestAlertException("A new banner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        banner = bannerRepository.save(banner);
        return ResponseEntity.created(new URI("/api/banners/" + banner.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, banner.getId().toString()))
            .body(banner);
    }

    /**
     * {@code PUT  /banners/:id} : Updates an existing banner.
     *
     * @param id the id of the banner to save.
     * @param banner the banner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated banner,
     * or with status {@code 400 (Bad Request)} if the banner is not valid,
     * or with status {@code 500 (Internal Server Error)} if the banner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Banner> updateBanner(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Banner banner
    ) throws URISyntaxException {
        log.debug("REST request to update Banner : {}, {}", id, banner);
        if (banner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, banner.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bannerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        banner = bannerRepository.save(banner);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, banner.getId().toString()))
            .body(banner);
    }

    /**
     * {@code PATCH  /banners/:id} : Partial updates given fields of an existing banner, field will ignore if it is null
     *
     * @param id the id of the banner to save.
     * @param banner the banner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated banner,
     * or with status {@code 400 (Bad Request)} if the banner is not valid,
     * or with status {@code 404 (Not Found)} if the banner is not found,
     * or with status {@code 500 (Internal Server Error)} if the banner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Banner> partialUpdateBanner(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Banner banner
    ) throws URISyntaxException {
        log.debug("REST request to partial update Banner partially : {}, {}", id, banner);
        if (banner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, banner.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bannerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Banner> result = bannerRepository
            .findById(banner.getId())
            .map(existingBanner -> {
                if (banner.getLabel() != null) {
                    existingBanner.setLabel(banner.getLabel());
                }
                if (banner.getImages() != null) {
                    existingBanner.setImages(banner.getImages());
                }
                if (banner.getCreatedAt() != null) {
                    existingBanner.setCreatedAt(banner.getCreatedAt());
                }
                if (banner.getUpdatedAt() != null) {
                    existingBanner.setUpdatedAt(banner.getUpdatedAt());
                }

                return existingBanner;
            })
            .map(bannerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, banner.getId().toString())
        );
    }

    /**
     * {@code GET  /banners} : get all the banners.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of banners in body.
     */
    @GetMapping("")
    public List<Banner> getAllBanners() {
        log.debug("REST request to get all Banners");
        return bannerRepository.findAll();
    }

    /**
     * {@code GET  /banners/:id} : get the "id" banner.
     *
     * @param id the id of the banner to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the banner, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Banner> getBanner(@PathVariable("id") Long id) {
        log.debug("REST request to get Banner : {}", id);
        Optional<Banner> banner = bannerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(banner);
    }

    /**
     * {@code DELETE  /banners/:id} : delete the "id" banner.
     *
     * @param id the id of the banner to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable("id") Long id) {
        log.debug("REST request to delete Banner : {}", id);
        bannerRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
