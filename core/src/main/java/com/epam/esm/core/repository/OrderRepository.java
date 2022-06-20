package com.epam.esm.core.repository;

import com.epam.esm.core.entity.Order;

import java.util.Optional;

public interface OrderRepository {
    /**
     * Adds an order.
     * @param order order to add
     * @return added order
     */
    Order addOrder(Order order);

    /**
     * Returns an order by id.
     * @param id id to find by
     * @return order with corresponding id
     */
    Optional<Order> getOrderById(long id);
}
