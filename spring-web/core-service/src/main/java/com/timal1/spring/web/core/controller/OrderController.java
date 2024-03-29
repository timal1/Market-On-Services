package com.timal1.spring.web.core.controller;


import com.timal1.spring.web.api.core.OrderDetailsDto;
import com.timal1.spring.web.api.core.OrderDto;
import com.timal1.spring.web.api.exeptions.ResourceNotFoundException;
import com.timal1.spring.web.core.services.OrderService;
import com.timal1.spring.web.core.converters.OrderConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Создание нового заказа",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username,
                            @RequestBody OrderDetailsDto orderDetailsDto) {
         orderService.createOrder(username, orderDetailsDto);
    }

    @Operation(
            summary = "Получение списка заказов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDto.class)))
                    )
            }
    )
    @GetMapping
    public List<OrderDto> getCurrentUserOrders(@RequestHeader String username) {
        return orderService.findOrdersByUserName(username).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @Operation(
            summary = "Получение заказа по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = OrderDto.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable @Parameter(description = "Идентификатор заказа", required = true)Long id) {
        return orderConverter.entityToDto(orderService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ORDER 404")));
    }

    @Operation(
            summary = "Отмена заказа",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/cancel/{id}")
    public void cancelOrderById(@PathVariable @Parameter(description = "Идентификатор заказа", required = true)Long id) {
        orderService.cancelOrderById(id);
    }


    @Operation(
            summary = "Получение списка популярных купленных продуктов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))
                    )
            }
    )
    @GetMapping("/get_best_products/{amountDay}/{amountProducts}")
    public List<String> getFavoriteProductsMonth(@PathVariable @Parameter(description = "Количество дней", required = true) int amountDay,
                                                 @PathVariable @Parameter(description = "Количество товаров для вывода", required = true)int amountProducts) {
        return orderService.findFavoritesProducts(amountProducts);
    }
}
