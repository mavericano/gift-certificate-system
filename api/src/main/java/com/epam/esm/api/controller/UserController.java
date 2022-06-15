package com.epam.esm.api.controller;

import com.epam.esm.core.dto.OrderDto;
import com.epam.esm.core.dto.UserDto;
import com.epam.esm.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    final UserService userService;
    final OrderController orderController;

    public UserController(UserService userService, OrderController orderController) {
        this.userService = userService;
        this.orderController = orderController;
    }

    @GetMapping(params = {"page", "size"})
    public List<UserDto> getAllUsers(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam(name = "sortBy", required = false) String sortBy, @RequestParam(name = "sortType", required = false) String sortType) {
        return userService.getAllUsers(page, size, sortBy, sortType).stream().map(this::addLinksToUser).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable String id) {
        return addLinksToUser(userService.getUserById(id));
    }

    @GetMapping(path = "/{id}/orders", params = {"page", "size"})
    public List<OrderDto> getOrdersForUserById(@PathVariable String id, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam(name = "sortBy", required = false) String sortBy, @RequestParam(name = "sortType", required = false) String sortType) {
        return userService.getOrdersForUserById(id, page, size, sortBy, sortType).stream().map(orderController::addLinksToOrder).collect(Collectors.toList());
    }

    private UserDto addLinksToUser(UserDto userDto) {
        userDto.add(linkTo(methodOn(UserController.class)
                        .getUserById(String.valueOf(userDto.getId()))).withSelfRel());
        userDto.add(linkTo(methodOn(UserController.class)
                        .getOrdersForUserById(String.valueOf(userDto.getId()), 1, 5, "orderId", "asc")).withRel("orders"));
        return userDto;
    }
}
