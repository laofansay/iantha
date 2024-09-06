package com.laofan.iantha.service;

import cn.hutool.core.util.NumberUtil;
import com.laofan.iantha.domain.Order;
import com.laofan.iantha.domain.OrderItem;
import com.laofan.iantha.domain.Product;
import com.laofan.iantha.repository.CartItemRepository;
import com.laofan.iantha.repository.OrderRepository;
import com.laofan.iantha.repository.ProductRepository;
import com.laofan.iantha.security.FanSecurityUtils;
import com.laofan.iantha.security.SecurityUtils;
import com.laofan.iantha.service.dto.OrderDTO;
import com.laofan.iantha.service.mapper.OrderMapper;
import com.laofan.iantha.service.vo.CartItemVO;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.laofan.iantha.domain.Order}.
 */
@Service
@Transactional
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    @Resource
    ProductRepository productRepository;

    @Resource
    CartItemRepository cartItemRepository;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    /**
     * Save a order.
     *
     * @param orderDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderDTO save(OrderDTO orderDTO) {
        log.debug("Request to save Order : {}", orderDTO);
        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    /**
     * Update a order.
     *
     * @param orderDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderDTO update(OrderDTO orderDTO) {
        log.debug("Request to update Order : {}", orderDTO);
        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    /**
     * Partially update a order.
     *
     * @param orderDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrderDTO> partialUpdate(OrderDTO orderDTO) {
        log.debug("Request to partially update Order : {}", orderDTO);

        return orderRepository
            .findById(orderDTO.getId())
            .map(existingOrder -> {
                orderMapper.partialUpdate(existingOrder, orderDTO);

                return existingOrder;
            })
            .map(orderRepository::save)
            .map(orderMapper::toDto);
    }

    /**
     * Get all the orders.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> findAll() {
        log.debug("Request to get all Orders");
        return orderRepository.findAll().stream().map(orderMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one order by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrderDTO> findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        return orderRepository.findById(id).map(orderMapper::toDto);
    }

    /**
     * Delete the order by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.deleteById(id);
    }

    public OrderDTO createOrder(@Valid CartItemVO cartItemVO) {
        List<Long> ids = new ArrayList<>();
        Order order = new Order();
        Set<OrderItem> list = new HashSet<>();

        cartItemVO
            .getCartItemDTOList()
            .forEach(e -> {
                if (e.getCid().equals(FanSecurityUtils.getCurrentIdent())) {
                    OrderItem item = new OrderItem();
                    item.setCount(e.getCount());
                    Product product = productRepository.getReferenceById(Long.parseLong(e.getProdId()));
                    item.setProduct(product);
                    item.setDiscount(product.getDiscount());
                    item.setPrice(product.getPrice());
                    BigDecimal total = NumberUtil.mul(product.getPrice(), (e.getCount() * (100 - product.getDiscount())) / 100);
                    item.setTotal(total);
                    BigDecimal disTotal = NumberUtil.mul(product.getPrice(), (e.getCount() * (product.getDiscount())) / 100);
                    item.setDisTotal(disTotal);
                    list.add(item);
                    ids.add(e.getId());
                }
            });

        order.setOrderItems(list);
        order.setCreatedAt(Instant.now());

        order.setNumber(0);
        order.setPaid(false);
        order.setCreatedAt(Instant.now());

        BigDecimal totalAmount = list.stream().map(OrderItem::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotal(totalAmount);

        BigDecimal disAmount = list.stream().map(OrderItem::getDisTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotal(disAmount);

        order = orderRepository.save(order);
        OrderDTO dto = orderMapper.toDto(order);
        //clear cart item
        cartItemRepository.deleteAllById(ids);
        //收货地址
        return dto;
    }
}
