package com.epam.esm.core.repository.impl;

import com.epam.esm.core.entity.Order;
import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.InvalidPageSizeException;
import com.epam.esm.core.exception.InvalidSortParamsException;
import com.epam.esm.core.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryHibernateImpl implements OrderRepository {

    final EntityManager entityManager;

    @Override
    public List<Order> getAllOrders(int page, int size) {
        TypedQuery<Long> countQuery = entityManager.createQuery("select count(order1) from Order order1", Long.class);
        int lastPageNumber = (int) Math.ceil((double)countQuery.getSingleResult() / size);
        lastPageNumber = lastPageNumber == 0 ? 1 : lastPageNumber;
        if (page > lastPageNumber) throw new InvalidPageSizeException("pageNumberTooBigExceptionMessage");

        TypedQuery<Order> query = entityManager.createQuery("from Order", Order.class);

        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        return query.getResultList();
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
