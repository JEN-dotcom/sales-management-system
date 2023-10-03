package com.ingryd.sms.model;

import com.ingryd.sms.entity.Product;

import lombok.Getter;

@Getter
public class OrderItemDTO {
    private Product product;
    private int quantity;
}
