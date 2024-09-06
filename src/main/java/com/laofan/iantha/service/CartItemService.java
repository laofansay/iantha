package com.laofan.iantha.service;

import com.laofan.iantha.domain.CartItem;
import com.laofan.iantha.repository.CartItemRepository;
import com.laofan.iantha.service.dto.CartItemDTO;
import com.laofan.iantha.service.mapper.CartItemMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.laofan.iantha.domain.CartItem}.
 */
@Service
@Transactional
public class CartItemService {

    private static final Logger log = LoggerFactory.getLogger(CartItemService.class);

    private final CartItemRepository cartItemRepository;

    private final CartItemMapper cartItemMapper;

    public CartItemService(CartItemRepository cartItemRepository, CartItemMapper cartItemMapper) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
    }

    /**
     * Save a cartItem.
     *
     * @param cartItemDTO the entity to save.
     * @return the persisted entity.
     */
    public CartItemDTO save(CartItemDTO cartItemDTO) {
        log.debug("Request to save CartItem : {}", cartItemDTO);
        CartItem cartItem = cartItemMapper.toEntity(cartItemDTO);
        cartItem = cartItemRepository.save(cartItem);
        return cartItemMapper.toDto(cartItem);
    }

    /**
     * Update a cartItem.
     *
     * @param cartItemDTO the entity to save.
     * @return the persisted entity.
     */
    public CartItemDTO update(CartItemDTO cartItemDTO) {
        log.debug("Request to update CartItem : {}", cartItemDTO);
        CartItem cartItem = cartItemMapper.toEntity(cartItemDTO);
        cartItem = cartItemRepository.save(cartItem);
        return cartItemMapper.toDto(cartItem);
    }

    /**
     * Partially update a cartItem.
     *
     * @param cartItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CartItemDTO> partialUpdate(CartItemDTO cartItemDTO) {
        log.debug("Request to partially update CartItem : {}", cartItemDTO);

        return cartItemRepository
            .findById(cartItemDTO.getId())
            .map(existingCartItem -> {
                cartItemMapper.partialUpdate(existingCartItem, cartItemDTO);

                return existingCartItem;
            })
            .map(cartItemRepository::save)
            .map(cartItemMapper::toDto);
    }

    /**
     * Get all the cartItems.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CartItemDTO> findAll() {
        log.debug("Request to get all CartItems");
        LinkedList<CartItemDTO> collect = cartItemRepository
            .findAll()
            .stream()
            .map(cartItemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
        return collect;
    }

    /**
     * Get one cartItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CartItemDTO> findOne(Long id) {
        log.debug("Request to get CartItem : {}", id);
        return cartItemRepository.findById(id).map(cartItemMapper::toDto);
    }

    /**
     * Delete the cartItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CartItem : {}", id);
        cartItemRepository.deleteById(id);
    }
}
