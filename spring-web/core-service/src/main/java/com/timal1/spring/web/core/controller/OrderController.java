package com.timal1.spring.web.core.controller;


import com.timal1.spring.web.api.dto.Cart;
import com.timal1.spring.web.core.dto.OrderDetailsDto;
import com.timal1.spring.web.core.dto.OrderDto;
import com.timal1.spring.web.core.services.OrderService;
import com.timal1.spring.web.core.converters.OrderConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username,
                            @RequestParam(name = "address", required = false) String address,
                            @RequestParam(name = "phone", required = false) String phone,
                            @RequestBody Cart cart) {
         orderService.createOrder(username, address, phone, cart);
    }

    @GetMapping
    public List<OrderDto> getCurrentUserOrders(@RequestHeader String username) {
        return orderService.findOrdersByUserName(username).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }
}
