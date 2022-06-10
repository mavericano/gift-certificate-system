package com.epam.esm.core.repository.impl;

import com.epam.esm.core.entity.Order;
import com.epam.esm.core.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Repository
public class OrderRepositoryHibernateImpl implements OrderRepository {

    final EntityManager entityManager;

    public OrderRepositoryHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Order addOrder(Order order) {
        LocalDateTime now = LocalDateTime.now();
        order.setPurchaseTime(now);
        entityManager.persist(order);
        return order;
    }
}
