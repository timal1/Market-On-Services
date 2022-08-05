package com.timal1.spring.web.api.carts;

import java.util.List;

public class CartDto {
    private List<CartItemDto> items;
    private Double totalPrice;

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public CartDto() {
    }

    public CartDto(List<CartItemDto> items, Double totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
    }
}
