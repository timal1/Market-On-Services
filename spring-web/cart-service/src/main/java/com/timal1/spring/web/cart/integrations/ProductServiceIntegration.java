package com.timal1.spring.web.cart.integrations;

import com.timal1.spring.web.api.core.ProductDto;
import com.timal1.spring.web.api.exeptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final WebClient productServiceWebClient;

    public Optional<ProductDto> getProductById(Long id) {
        ProductDto productDto = productServiceWebClient.get()
                .uri("/api/v1/products/" + id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                error -> Mono.error(new ResourceNotFoundException("Продукт с id: " + id + " не найден")))
                .bodyToMono(ProductDto.class)
                .block();
        return Optional.ofNullable(productDto);
    }

    public List<ProductDto> getFavoriteProductsByListId(List<Long> list) {

        List<ProductDto> favoriteProducts = productServiceWebClient.post()
                .uri("/api/v1/products/get_best_products")
                .attribute("list", list)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProductDto>>() {})
                .block();
        return  favoriteProducts;
    }
}
