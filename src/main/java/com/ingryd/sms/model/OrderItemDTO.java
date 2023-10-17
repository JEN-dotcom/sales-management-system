package com.ingryd.sms.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItemDTO {
    private String name;
    private String brand;
    private int quantity;
}
