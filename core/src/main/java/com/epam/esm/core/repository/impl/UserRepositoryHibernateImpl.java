package com.epam.esm.core.repository.impl;

import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.InvalidPageSizeException;
import com.epam.esm.core.repository.UserRepository;
import org.apache.commons.lang3.reflect.Typed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
    public List<User> getAllUsers(int page, int size) {
        TypedQuery<Long> countQuery = entityManager.createQuery("select count(user) from User user", Long.class);
        int lastPageNumber = (int) Math.ceil((double)countQuery.getSingleResult() / size);
        lastPageNumber = lastPageNumber == 0 ? 1 : lastPageNumber;
        if (page > lastPageNumber) throw new InvalidPageSizeException("pageNumberTooBigExceptionMessage");

        TypedQuery<User> query = entityManager.createQuery("from User", User.class);
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        return query.getResultList();
    }
}
