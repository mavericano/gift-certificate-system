package com.epam.esm.core.repository.impl;

import com.epam.esm.core.entity.Order;
import com.epam.esm.core.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class OrderRepositoryHibernateImpl implements OrderRepository {

    final EntityManager entityManager;

    public OrderRepositoryHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Order addOrder(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public Optional<Order> getOrderById(long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }
}
