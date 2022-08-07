package com.timal1.spring.web.cart.controllers;

import com.timal1.spring.web.api.carts.CartDto;
import com.timal1.spring.web.api.dto.StringResponse;
import com.timal1.spring.web.api.exeptions.AppError;
import com.timal1.spring.web.api.exeptions.ResourceNotFoundException;
import com.timal1.spring.web.cart.converters.CartConverter;
import com.timal1.spring.web.cart.exeptions.GlobalExceptionHandler;
import com.timal1.spring.web.cart.services.CartService;
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

    @GetMapping("/{uuid}")
    public CartDto getCart(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        return cartConverter.modelToDto(cartService.getCurrentCart(cartService.getCurrentCartUuid(username, uuid)));
    }

    @GetMapping("/generate")
    public StringResponse getCart() {
        return new StringResponse(cartService.generateCartUuid());
    }

    @GetMapping("/{uuid}/add/{productId}")
    public ResponseEntity<?> add(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId) {
        try {
            cartService.addToCart(cartService.getCurrentCartUuid(username, uuid), productId);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (WebClientResponseException we) {
            return new ResponseEntity<>(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Сервер продуктов не отвечает"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    @GetMapping("/{uuid}/decrement/{productId}")
    public void decrement(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.decrementItem(cartService.getCurrentCartUuid(username, uuid), productId);
    }

    @GetMapping("/{uuid}/remove/{productId}")
    public void remove(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId) {
        cartService.removeItemFromCart(cartService.getCurrentCartUuid(username, uuid), productId);
    }

    @GetMapping("/{uuid}/clear")
    public void clear(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        cartService.clearCart(cartService.getCurrentCartUuid(username, uuid));
    }

    @GetMapping("/{uuid}/merge")
    public void merge(@RequestHeader String username, @PathVariable String uuid) {
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
