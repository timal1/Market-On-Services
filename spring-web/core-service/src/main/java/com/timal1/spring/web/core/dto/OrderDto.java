package com.timal1.spring.web.core.dto;
import com.timal1.spring.web.api.dto.OrderItemDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private Double totalPrice;
    private String address;
    private String phone;
    private String userName;
    private List<OrderItemDto> items;
}
