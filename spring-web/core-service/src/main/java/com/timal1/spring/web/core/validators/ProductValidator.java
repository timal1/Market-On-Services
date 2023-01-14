package com.timal1.spring.web.core.validators;


import com.timal1.spring.web.api.dto.ProductDto;
import com.timal1.spring.web.core.exeptions.ValidationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidator {

    public void validate(ProductDto productDto) {
        List<String> errors = new ArrayList<>();

        if (productDto.getPrice() < 1) {
            errors.add("Price product not is less than 1");
        }
        if (productDto.getTitle().isBlank()) {
            errors.add("Product can not have an empty title");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
