package com.epam.esm.core.repository;

import com.epam.esm.core.entity.User;

import java.util.List;
import java.util.Optional;
//TODO javadoc
public interface UserRepository {
    Optional<User> getUserById(long id);

    List<User> getAllUsers(int page, int size, String sortBy, String sortType);

    User getMaxOrderSumUser();
}
