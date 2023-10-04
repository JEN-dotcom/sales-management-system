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
    @NotNull
    @Column(name = "product_name")
    private String name;

    @NotBlank
    @NotNull
    @NotEmpty
    @Column(name = "product_price")
    private double price;

    @NotBlank
    @NotNull
    @Column(name = "description")
    private String description;


    @NotBlank
    @NotNull
    @Column(name = "stock_qty")
    private int stock;

    @NotBlank
    @NotNull
    @NotEmpty
    @Column(name = "brand_name")
    private String brand;

    @NotBlank
    @NotNull
    @NotEmpty
    @Column(name = "category")
    private String category;

    @Column(name = "discount_%")
    private int discount;

}
