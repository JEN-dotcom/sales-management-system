package com.ingryd.sms.repository;

import com.ingryd.sms.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Product findById(Long id);
    List<Product> findProductByCategory(String category);
    List<Product> findProductByName(String name);
}
