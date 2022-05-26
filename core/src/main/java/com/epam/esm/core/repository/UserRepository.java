package com.epam.esm.core.repository;

import com.epam.esm.core.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> getUserById(long id);

    List<User> getAllUsers();
}
