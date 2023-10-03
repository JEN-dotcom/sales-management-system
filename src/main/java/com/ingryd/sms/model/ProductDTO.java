package com.ingryd.sms.model;

import com.ingryd.sms.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private String name;

    private double price;

    private String description;

    private int stock;

    private String brand;

    private String category;

    private int discount;

}
