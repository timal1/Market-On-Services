package com.timal1.spring.web.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String title;
    private Double price;
    private Integer amount;


    public ProductDto(Long id, String title, Double price) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.amount = 1;
    }
}
