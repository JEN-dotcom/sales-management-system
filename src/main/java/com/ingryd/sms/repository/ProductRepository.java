package com.ingryd.sms.repository;

import com.ingryd.sms.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
    List<Product> findByCategory(String category);
    List<Product> findByName(String name);
    void deleteById(Long id);

    @Query("SELECT p FROM Product p WHERE p.name = :value1 AND p.brand = :value2")
    Product findByNameAndBrand(String value1, String value2);

}
