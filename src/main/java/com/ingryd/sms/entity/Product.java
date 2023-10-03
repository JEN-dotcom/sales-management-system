package com.ingryd.sms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private long id;

    @NotBlank
    @Column(name = "product_name")
    private String name;

    @NotBlank
    @NotEmpty
    @Column(name = "product_price")
    private double price;

    @NotBlank
    @Column(name = "description")
    private String description;

    @NotBlank
    @Column(name = "stock_qty")
    private int stock;

    @NotBlank
    @NotEmpty
    @Column(unique = true, name = "brand_name")
    private String brand;

    @NotBlank
    @NotEmpty
    @Column(name = "category")
    private String category;

    @Column(name = "discount_%")
    private int discount;

}
