package com.timal1.spring.web.api.core;


import java.util.List;

public class OrderDto {
    private Long id;
    private Double totalPrice;
    private String address;
    private String phone;
    private String userName;
    private List<OrderItemDto> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public OrderDto() {
    }

    public OrderDto(Long id, Double totalPrice, String address, String phone, String userName, List<OrderItemDto> items) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.address = address;
        this.phone = phone;
        this.userName = userName;
        this.items = items;
    }
}
