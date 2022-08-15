package com.epam.esm.api.controller;

import com.epam.esm.core.dto.OrderDto;
import com.epam.esm.core.dto.OrderRequestDto;
import com.epam.esm.core.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    final OrderService orderService;

    @GetMapping(params = {"page", "size"})
    public List<OrderDto> getAllOrders(@RequestParam("page") int page, @RequestParam("size") int size) {
        return orderService.getAllOrders(page, size).stream().map(this::addLinksToOrder).collect(Collectors.toList());
    }

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
