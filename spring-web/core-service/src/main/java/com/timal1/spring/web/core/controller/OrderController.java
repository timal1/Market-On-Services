package com.timal1.spring.web.core.controller;


import com.timal1.spring.web.api.core.OrderDetailsDto;
import com.timal1.spring.web.api.core.OrderDto;
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
                            @RequestBody OrderDetailsDto orderDetailsDto) {
         orderService.createOrder(username, orderDetailsDto);
    }

    @GetMapping
    public List<OrderDto> getCurrentUserOrders(@RequestHeader String username) {
        return orderService.findOrdersByUserName(username).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/get_best_products/{amountDay}/{amountProducts}")
    public List<String> getFavoriteProductsMonth(@PathVariable int amountDay, @PathVariable int amountProducts) {
        return orderService.findFavoritesProducts(amountProducts);
    }
}
