package com.epam.esm.api.controller;

import com.epam.esm.core.dto.OrderDto;
import com.epam.esm.core.dto.OrderRequestDto;
import com.epam.esm.core.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto placeOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        return orderService.placeOrder(orderRequestDto);
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id);
    }

    protected OrderDto addLinksToOrder(OrderDto orderDto) {
        orderDto.add(linkTo(methodOn(OrderController.class)
                        .getOrderById(String.valueOf(orderDto.getOrderId()))).withSelfRel());
        return orderDto;
    }
}
