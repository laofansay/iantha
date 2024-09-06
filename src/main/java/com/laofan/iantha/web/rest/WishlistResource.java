package com.laofan.iantha.web.rest;

import com.laofan.iantha.domain.Wishlist;
import com.laofan.iantha.repository.WishlistRepository;
import com.laofan.iantha.security.FanSecurityUtils;
import com.laofan.iantha.security.SecurityUtils;
import com.laofan.iantha.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.antlr.v4.runtime.IntStream;
import org.apache.commons.lang3.IntegerRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.laofan.iantha.domain.Wishlist}.
 */
@RestController
@RequestMapping("/api/wishlists")
@Transactional
public class WishlistResource {

    private static final Logger log = LoggerFactory.getLogger(WishlistResource.class);

    private static final String ENTITY_NAME = "wishlist";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WishlistRepository wishlistRepository;

    public WishlistResource(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    /**
     * {@code POST  /wishlists} : Create a new wishlist.
     *
     * @param wishlist the wishlist to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wishlist, or with status {@code 400 (Bad Request)} if the wishlist has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Wishlist> createWishlist(@Valid @RequestBody Wishlist wishlist) throws URISyntaxException {
        log.debug("REST request to save Wishlist : {}", wishlist);
        if (wishlist.getId() != null) {
            throw new BadRequestAlertException("A new wishlist cannot already have an ID", ENTITY_NAME, "idexists");
        }

        wishlist.setIdentify(FanSecurityUtils.getCurrentIdent());
        wishlist.setCreatedDate(Instant.now());
        wishlist = wishlistRepository.save(wishlist);
        return ResponseEntity.created(new URI("/api/wishlists/" + wishlist.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, wishlist.getId().toString()))
            .body(wishlist);
    }

    /**
     * {@code PUT  /wishlists/:id} : Updates an existing wishlist.
     *
     * @param id the id of the wishlist to save.
     * @param wishlist the wishlist to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wishlist,
     * or with status {@code 400 (Bad Request)} if the wishlist is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wishlist couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Wishlist> updateWishlist(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Wishlist wishlist
    ) throws URISyntaxException {
        log.debug("REST request to update Wishlist : {}, {}", id, wishlist);
        if (wishlist.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wishlist.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wishlistRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        wishlist.setIdentify(FanSecurityUtils.getCurrentIdent());
        wishlist = wishlistRepository.save(wishlist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wishlist.getId().toString()))
            .body(wishlist);
    }

    /**
     * {@code PATCH  /wishlists/:id} : Partial updates given fields of an existing wishlist, field will ignore if it is null
     *
     * @param id the id of the wishlist to save.
     * @param wishlist the wishlist to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wishlist,
     * or with status {@code 400 (Bad Request)} if the wishlist is not valid,
     * or with status {@code 404 (Not Found)} if the wishlist is not found,
     * or with status {@code 500 (Internal Server Error)} if the wishlist couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Wishlist> partialUpdateWishlist(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Wishlist wishlist
    ) throws URISyntaxException {
        log.debug("REST request to partial update Wishlist partially : {}, {}", id, wishlist);
        if (wishlist.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wishlist.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wishlistRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<Wishlist> result = wishlistRepository
            .findById(wishlist.getId())
            .map(existingWishlist -> {
                if (wishlist.getIdentify() != null) {
                    existingWishlist.setIdentify(wishlist.getIdentify());
                }
                if (wishlist.getBizCode() != null) {
                    existingWishlist.setBizCode(wishlist.getBizCode());
                }
                if (wishlist.getBizDesc() != null) {
                    existingWishlist.setBizDesc(wishlist.getBizDesc());
                }
                if (wishlist.getBizIcon() != null) {
                    existingWishlist.setBizIcon(wishlist.getBizIcon());
                }
                if (wishlist.getBizTitle() != null) {
                    existingWishlist.setBizTitle(wishlist.getBizTitle());
                }
                if (wishlist.getLink() != null) {
                    existingWishlist.setLink(wishlist.getLink());
                }
                if (wishlist.getFavCate() != null) {
                    existingWishlist.setFavCate(wishlist.getFavCate());
                }
                if (wishlist.getCreatedDate() != null) {
                    existingWishlist.setCreatedDate(wishlist.getCreatedDate());
                }

                return existingWishlist;
            })
            .map(wishlistRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wishlist.getId().toString())
        );
    }

    /**
     * {@code GET  /wishlists} : get all the wishlists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wishlists in body.
     */
    @GetMapping("")
    public List<Wishlist> getAllWishlists(Wishlist wishlist) {
        log.debug("REST request to get all Wishlists");
        wishlist.setIdentify(FanSecurityUtils.getCurrentIdent());
        return wishlistRepository.findAll(Example.of(wishlist));
    }

    /**
     * {@code GET  /wishlists/:id} : get the "id" wishlist.
     *
     * @param id the id of the wishlist to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wishlist, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Wishlist> getWishlist(@PathVariable("id") Long id) {
        log.debug("REST request to get Wishlist : {}", id);
        Optional<Wishlist> wishlist = wishlistRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(wishlist);
    }

    /**
     * {@code DELETE  /wishlists/:id} : delete the "id" wishlist.
     *
     * @param id the id of the wishlist to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable("id") Long id) {
        log.debug("REST request to delete Wishlist : {}", id);
        wishlistRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
