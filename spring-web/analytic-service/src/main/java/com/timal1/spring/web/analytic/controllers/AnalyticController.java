package com.timal1.spring.web.analytic.controllers;

import com.timal1.spring.web.analytic.services.AnalyticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/analytic")
public class AnalyticController {
    private final AnalyticService analyticService;

    @GetMapping("/{service}/get_best_products/{amountDay}/{amountProducts}")
    public List<String> getFavoriteProductsMonth(@PathVariable String service, @PathVariable int amountDay, @PathVariable int amountProducts) {
        return analyticService.getFavoriteProductsMonth(service, amountDay, amountProducts);
    }
}
