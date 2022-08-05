package com.timal1.spring.web.analytic.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceIntegration {
    private final WebClient serviceWebClient;

    public List<String> getFavoriteProductsByIdFromCart(String service, int amountDay, int amountProducts) {

        List<String> favoriteProducts = serviceWebClient.get()
                .uri(service + "/api/v1/" + service + "/get_best_products/" + amountDay + "/" + amountProducts)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {})
                .block();
        return  favoriteProducts;
    }

    public List<String> getFavoriteProductsByIdFromOrders(String service, int amountDay, int amountProducts) {

        List<String> favoriteProducts = serviceWebClient.get()
                .uri("core/api/v1/" + service + "/get_best_products/" + amountDay + "/" + amountProducts)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {})
                .block();
        return  favoriteProducts;
    }
}
