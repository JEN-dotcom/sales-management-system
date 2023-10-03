package com.ingryd.sms.repository;

import com.ingryd.sms.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(long id);
    List<Product> findProductByCategory(String category);
    List<Product> findProductByName(String name);
}
