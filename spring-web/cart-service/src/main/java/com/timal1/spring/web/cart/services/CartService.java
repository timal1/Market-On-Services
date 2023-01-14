package com.timal1.spring.web.cart.services;

import com.timal1.spring.web.api.core.ProductDto;
import com.timal1.spring.web.api.exeptions.ResourceNotFoundException;
import com.timal1.spring.web.cart.entities.CartItemEntity;
import com.timal1.spring.web.cart.integrations.ProductServiceIntegration;
import com.timal1.spring.web.cart.models.Cart;
import com.timal1.spring.web.cart.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;
    private final CartRepository cartRepository;

    @Value("${utils.cart.prefix}")
    private String cartPrefix;

    public String getCartUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }

    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

    public Cart getCurrentCart(String cartKey) {
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(cartKey);
    }

    public void addToCart(String cartKey, Long productId) throws ResourceNotFoundException{
            ProductDto productDto = productServiceIntegration.getProductById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Продукт не найдет с id: " + productId));
            execute(cartKey, c -> {
                c.add(productDto);
            });
        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setProductId(productDto.getId());
        cartItemEntity.setProductTitle(productDto.getTitle());
        cartItemEntity.setPrice(productDto.getPrice());
        cartItemEntity.setCartKey(cartKey);
        cartRepository.save(cartItemEntity);

    }

    public void clearCart(String cartKey) {
        execute(cartKey, Cart::clear);
    }

    public void removeItemFromCart(String cartKey, Long productId) {
        execute(cartKey, c -> c.remove(productId));
    }

    public void decrementItem(String cartKey, Long productId) {
        execute(cartKey, c -> c.decrement(productId));
    }

    public void merge(String userCartKey, String guestCartKey) {
        Cart guestCart = getCurrentCart(guestCartKey);
        Cart userCart = getCurrentCart(userCartKey);
        userCart.merge(guestCart);
        updateCart(guestCartKey, guestCart);
        updateCart(userCartKey, userCart);
    }

    private void execute(String cartKey, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartKey);
        action.accept(cart);
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    public void updateCart(String cartKey, Cart cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    public List<String> findFavoritesProducts(int amountDay, int amountProducts) {
        return cartRepository.findFavoritesProducts(amountProducts, amountDay);
    }

    public String getCurrentCartUuid(String username, String uuid) {
        if (username != null) {
            return getCartUuidFromSuffix(username);
        }
        return getCartUuidFromSuffix(uuid);
    }
}
