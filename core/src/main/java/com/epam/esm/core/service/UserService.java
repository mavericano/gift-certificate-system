package com.epam.esm.core.service;


import com.epam.esm.core.entity.User;

import java.util.List;

public interface UserService {
    User getUserById(String id);
    List<User> getAllUsers();
}
