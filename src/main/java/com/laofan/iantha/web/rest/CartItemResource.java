package com.laofan.iantha.web.rest;

import com.laofan.iantha.domain.CartItem;
import com.laofan.iantha.domain.Product;
import com.laofan.iantha.repository.CartItemRepository;
import com.laofan.iantha.repository.ProductRepository;
import com.laofan.iantha.security.SecurityUtils;
import com.laofan.iantha.service.CartItemService;
import com.laofan.iantha.service.dto.CartItemDTO;
import com.laofan.iantha.service.mapper.CartItemMapper;
import com.laofan.iantha.web.rest.errors.BadRequestAlertException;
import jakarta.annotation.Resource;
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
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.laofan.iantha.domain.CartItem}.
 */
@RestController
@RequestMapping("/api/cart-items")
public class CartItemResource {

    private static final Logger log = LoggerFactory.getLogger(CartItemResource.class);

    private static final String ENTITY_NAME = "cartItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CartItemService cartItemService;

    private final CartItemRepository cartItemRepository;
    @Resource
    ProductRepository productRepository;

    @Resource
    CartItemMapper cartItemMapper;

    public CartItemResource(CartItemService cartItemService, CartItemRepository cartItemRepository) {
        this.cartItemService = cartItemService;
        this.cartItemRepository = cartItemRepository;
    }

    /**
     * {@code POST  /cart-items} : Create a new cartItem.
     *
     * @param dto the cartItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cartItemDTO, or with status {@code 400 (Bad Request)} if the cartItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @Transactional
    public ResponseEntity<CartItemDTO> createCartItem(@Valid @RequestBody CartItemDTO dto) throws URISyntaxException {
        log.debug("REST request to save CartItem : {}", dto);

        dto.setCid(SecurityUtils.getCurrentUserLogin().get());

        CartItem cartItem = new CartItem();
        cartItem.setProdId(dto.getProdId());
        cartItem.setCid(dto.getCid());
        if(dto.getId()!=null || cartItemRepository.count(Example.of(cartItem))>0){
            cartItemRepository.updateCount(cartItem.getCid(), cartItem.getProdId(),dto.getCount());
            cartItem= cartItemRepository.findOne(Example.of(cartItem)).get();
        }else{
            Product product = productRepository.findById(Long.parseLong(cartItem.getProdId())).get();
            cartItem.setProduct(product);
            cartItem.setCount(dto.getCount());
            cartItem = cartItemRepository.save(cartItem);
        }

        return ResponseEntity.created(new URI("/api/cart-items/" + cartItem.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cartItem.getId().toString()))
            .body(cartItemMapper.toDto(cartItem));
    }

    /**
     * {@code PUT  /cart-items/:id} : Updates an existing cartItem.
     *
     * @param id the id of the cartItemDTO to save.
     * @param cartItemDTO the cartItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cartItemDTO,
     * or with status {@code 400 (Bad Request)} if the cartItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cartItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CartItemDTO> updateCartItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CartItemDTO cartItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CartItem : {}, {}", id, cartItemDTO);
        if (cartItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cartItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cartItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cartItemDTO = cartItemService.save(cartItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cartItemDTO.getId().toString()))
            .body(cartItemDTO);
    }



    /**
     * {@code GET  /cart-items} : get all the cartItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cartItems in body.
     */
    @GetMapping("")
    public List<CartItemDTO> getAllCartItems() {
        log.debug("REST request to get all CartItems");
        return cartItemService.findAll();
    }

    /**
     * {@code GET  /cart-items/:id} : get the "id" cartItem.
     *
     * @param id the id of the cartItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cartItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getCartItem(@PathVariable("id") Long id) {
        log.debug("REST request to get CartItem : {}", id);
        CartItem cart=new CartItem();
        cart.setProdId(id+"");
        cart.setCid(SecurityUtils.getCurrentUserLogin().get());
        CartItem cartItem = cartItemRepository.findOne(Example.of(cart)).orElse(new CartItem().count(0));

        return ResponseEntity.ok(cartItem);
    }

    /**
     * {@code DELETE  /cart-items/:id} : delete the "id" cartItem.
     *
     * @param id the id of the cartItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable("id") Long id) {
        log.debug("REST request to delete CartItem : {}", id);
        cartItemService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
