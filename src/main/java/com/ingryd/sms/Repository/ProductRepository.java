package com.ingryd.sms.Repository;

import com.ingryd.sms.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findProductById(long id);
    Optional<Product> findProductByCategory(String category);
    Optional<Product> findProductByName(String name);
}
