package com.epam.esm.api.controller;

import com.epam.esm.core.dto.OrderDto;
import com.epam.esm.core.dto.TagDto;
import com.epam.esm.core.dto.UserDto;
import com.epam.esm.core.entity.User;
import com.epam.esm.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/orders")
    public List<OrderDto> getOrdersForUserById(@PathVariable String id) {
        return userService.getOrdersForUserById(id);
    }

    @GetMapping("/{id}/top-tag")
    public TagDto getTopTagForUserById(@PathVariable String id) {
        return userService.getTopTagForUserById(id);
    }
}
