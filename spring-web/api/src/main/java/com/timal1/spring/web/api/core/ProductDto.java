package com.timal1.spring.web.api.core;

public class ProductDto {

    private Long id;
    private String title;
    private Double price;
    private Integer amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public ProductDto() {
    }

    public ProductDto(Long id, String title, Double price) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.amount = 1;
    }
}
