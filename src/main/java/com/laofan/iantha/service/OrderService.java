package com.laofan.iantha.service;

import com.laofan.iantha.domain.Order;
import com.laofan.iantha.repository.OrderRepository;
import com.laofan.iantha.security.SecurityUtils;
import com.laofan.iantha.service.dto.OrderDTO;
import com.laofan.iantha.service.dto.OrderFromDTO;
import com.laofan.iantha.service.mapper.OrderMapper;
import com.laofan.iantha.stripe.StrIpeApi;
import com.stripe.exception.StripeException;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
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

    private StrIpeApi stripeApi;

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
    public Order save(OrderDTO orderDTO) {
        log.debug("Request to save Order : {}", orderDTO);
        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        return order;
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

    @Resource
    CartItemService cartItemService;

    /**
     * 创建订单
     * 接口幂等
     * @param orderFromDTO
     * @return
     */
    public OrderDTO createOrder(OrderFromDTO orderFromDTO) throws StripeException {
        String email = SecurityUtils.getCurrentUserLogin().get();
        String name = "";
        String userId = "";

        //1 验证表单

        // 1 填充 orderFromDTO
        // 2 创建订单
        Order order = null; // this.save(userId,orderFromDTO.getAddress(),orderFromDTO.getCarts())
        //创建支付信息

        // 2 记录支付
        // 3 记录库存变动
        // 3 清空购物车
        cartItemService.clean(orderFromDTO.getCarts());

        // 创建 stripe 订单
        String url = stripeApi.createOrder(userId, email, name, order.getOrderItems());

        return null;
    }
}
