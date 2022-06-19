package com.epam.esm.core.repository.impl;

import com.epam.esm.core.entity.Order;
import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.InvalidPageSizeException;
import com.epam.esm.core.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryHibernateImpl implements UserRepository {

    private final EntityManager entityManager;

    public UserRepositoryHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> getUserById(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> getAllUsers(int page, int size, String sortBy, String sortType) {
        TypedQuery<Long> countQuery = entityManager.createQuery("select count(user) from User user", Long.class);
        int lastPageNumber = (int) Math.ceil((double)countQuery.getSingleResult() / size);
        lastPageNumber = lastPageNumber == 0 ? 1 : lastPageNumber;
        if (page > lastPageNumber) throw new InvalidPageSizeException("pageNumberTooBigExceptionMessage");

        TypedQuery<User> query;
        if (sortType != null) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root);
            try {
                criteriaQuery.orderBy(sortType.equalsIgnoreCase("ASC") ? criteriaBuilder.asc(root.get(sortBy)) : criteriaBuilder.desc(root.get(sortBy)));
            } catch (IllegalArgumentException e) {
//                FIXME add custom exception
                throw new RuntimeException("sortBy is not valid");
            }
            query = entityManager.createQuery(criteriaQuery);
        } else {
            query = entityManager.createQuery("from User", User.class);
        }
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public User getMaxOrderSumUser() {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
//        Root<User> root = criteriaQuery.from(User.class);
//        Join<User, Order> orderJoin = root.join("customer_id", JoinType.LEFT);
//
//        CriteriaQuery<BigDecimal> sumQuery = criteriaBuilder.createQuery(BigDecimal.class);
//        Root<Order> sumRoot = criteriaQuery.from(Order.class);
//        Path<User> userPath = sumRoot.get("customer_id");
//
//        sumQuery.select(criteriaBuilder.sum(sumRoot.get("finalPrice"))).where();
//
//
//        criteriaQuery.select(root);
//        criteriaBuilder.sum();
//        criteriaQuery;
        //                                                          select user from User user where max(
//                                                                    sum((select order.finalPrice from user.orders order))
//        select sum(o.finalPrice) from user.orders o where o.customer.id = user.id
//        TypedQuery<User> query = entityManager.createQuery("select user from User user group by user.id order by (select) desc", User.class);
        Query query = entityManager.createNativeQuery("SELECT * FROM user u ORDER BY (SELECT SUM(o.final_price) FROM `order` o WHERE u.user_id=o.customer_id) DESC", User.class);
//                "having sum((select o.finalPrice from user.orders o where o.customer.id = user.id)) >= all(select (o.finalPrice) from user.orders o where o.customer.id = user.id)", User.class);
        query.setMaxResults(1);
        return (User) query.getSingleResult();
//                query.getSingleResult();
    }
}
