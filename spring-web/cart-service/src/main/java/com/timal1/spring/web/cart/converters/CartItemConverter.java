package com.timal1.spring.web.cart.converters;

import com.timal1.spring.web.api.carts.CartItemDto;
import com.timal1.spring.web.cart.entities.CartItemEntity;
import com.timal1.spring.web.cart.models.CartItem;
import org.springframework.stereotype.Component;


@Component
public class CartItemConverter {
    public CartItemEntity modelToEntity(CartItem cartItem) {
        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setPrice(cartItem.getPrice());
        cartItemEntity.setProductId(cartItem.getProductId());
        cartItemEntity.setProductTitle(cartItem.getProductTitle());
        return cartItemEntity;
    }

    public CartItemDto entityToDto(CartItemEntity cartItemEntity) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId(cartItemEntity.getProductId());
        cartItemDto.setProductTitle(cartItemEntity.getProductTitle());
        cartItemDto.setPrice(cartItemEntity.getPrice());
        return cartItemDto;
    }
}
