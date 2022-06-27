package com.epam.esm.core.repository.impl;

import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.InvalidPageSizeException;
import com.epam.esm.core.exception.InvalidSortParamsException;
import com.epam.esm.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryHibernateImpl implements UserRepository {

    private final EntityManager entityManager;

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
                throw new InvalidSortParamsException("sortByNotFoundExceptionMessage");
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
        Query query = entityManager.createNativeQuery("SELECT * FROM user u ORDER BY (SELECT SUM(o.final_price) FROM `order` o WHERE u.user_id=o.customer_id) DESC", User.class);
        query.setMaxResults(1);
        return (User) query.getSingleResult();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("select user from User user where user.username=:username", User.class);
        query.setParameter("username", username);
        User user;
        try {
            user = query.getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }
        return Optional.ofNullable(user);
    }

    @Override
    public User addUser(User user) {
        entityManager.persist(user);
        return user;
    }
}
