package com.timal1.spring.web.analytic.services;

import com.timal1.spring.web.analytic.integration.ServiceIntegration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticService {
    private final ServiceIntegration serviceIntegration;

    public List<String> getFavoriteProductsMonth(String service, int amountDay, int amountProducts) {
        if (service.equals("cart")) {
            return serviceIntegration.getFavoriteProductsByIdFromCart(service, amountDay, amountProducts);
        }
        if (service.equals("orders")) {
            return serviceIntegration.getFavoriteProductsByIdFromOrders(service, amountDay, amountProducts);
        }
            return null;
    }
}
