package com.timal1.spring.web.cart.controllers;

import com.timal1.spring.web.api.carts.CartDto;
import com.timal1.spring.web.api.dto.StringResponse;
import com.timal1.spring.web.api.exeptions.AppError;
import com.timal1.spring.web.api.exeptions.ResourceNotFoundException;
import com.timal1.spring.web.cart.converters.CartConverter;
import com.timal1.spring.web.cart.exeptions.GlobalExceptionHandler;
import com.timal1.spring.web.cart.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @Operation(
            summary = "Запрос на получение корзины по uuid",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CartDto.class))
                    )
            }
    )
    @GetMapping("/{uuid}")
    public CartDto getCart(@RequestHeader(required = false) String username, @PathVariable @Parameter(description = "Идентификатор корзины", required = true) String uuid) {
        return cartConverter.modelToDto(cartService.getCurrentCart(cartService.getCurrentCartUuid(username, uuid)));
    }

    @Operation(
            summary = "Запрос на создание корзины",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    )
            }
    )
    @GetMapping("/generate")
    public StringResponse getCart() {
        return new StringResponse(cartService.generateCartUuid());
    }

    @Operation(
            summary = "Запрос на добавление продукта по id в корзину по UUID",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/{uuid}/add/{productId}")
    public ResponseEntity<?> add(@RequestHeader(required = false) String username,
                                 @PathVariable @Parameter(description = "Идентификатор корзины", required = true) String uuid,
                                 @PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long productId) {
        try {
            cartService.addToCart(cartService.getCurrentCartUuid(username, uuid), productId);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.toString(), e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (WebClientResponseException we) {
            return new ResponseEntity<>(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Сервер продуктов не отвечает"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    @Operation(
            summary = "Запрос на изменение количества продукта по id в корзину по UUID",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/{uuid}/decrement/{productId}")
    public void decrement(@RequestHeader(required = false) String username,
                          @PathVariable @Parameter(description = "Идентификатор корзины", required = true) String uuid,
                          @PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long productId)
    {
        cartService.decrementItem(cartService.getCurrentCartUuid(username, uuid), productId);
    }

    @Operation(
            summary = "Запрос на удаление продукта по id в корзину по UUID",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/{uuid}/remove/{productId}")
    public void remove(@RequestHeader(required = false) String username,
                       @PathVariable @Parameter(description = "Идентификатор корзины", required = true) String uuid,
                       @PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long productId)
    {
        cartService.removeItemFromCart(cartService.getCurrentCartUuid(username, uuid), productId);
    }

    @Operation(
            summary = "Запрос на очистку корзины по UUID",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/{uuid}/clear")
    public void clear(@RequestHeader(required = false) String username,
                      @PathVariable @Parameter(description = "Идентификатор корзины", required = true) String uuid) {
        cartService.clearCart(cartService.getCurrentCartUuid(username, uuid));
    }

    @Operation(
            summary = "Запрос на объединение корзины авторизованного пользователя и общей корзины по UUID",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/{uuid}/merge")
    public void merge(@RequestHeader String username,
                      @PathVariable @Parameter(description = "Идентификатор корзины", required = true) String uuid) {
        cartService.merge(
                cartService.getCurrentCartUuid(username, null),
                cartService.getCurrentCartUuid(null, uuid)
        );
    }

    @GetMapping("/get_best_products/{amountDay}/{amountProducts}")
    public List<String> getFavoriteProductsMonth(@PathVariable int amountDay, @PathVariable int amountProducts) {
        return cartService.findFavoritesProducts(amountDay, amountProducts);
    }
}
