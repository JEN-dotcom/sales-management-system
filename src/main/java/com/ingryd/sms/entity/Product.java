package com.ingryd.sms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @NotBlank
    @Column(name = "product_name")
    private String name;


    @Column(name = "product_price")
    private double price;

    @NotBlank
    @Column(name = "description")
    private String description;


    @Column(name = "stock_qty")
    private int stock;

    @NotBlank
    @Column(name = "brand_name")
    private String brand;

    @NotBlank
    @Column(name = "category")
    private String category;

    @Column(name = "discount_%")
    private int discount;

}
