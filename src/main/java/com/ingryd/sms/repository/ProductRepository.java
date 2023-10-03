package com.ingryd.sms.repository;

import com.ingryd.sms.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findProductById(long id);
    Product findProductByCategory(String category);
    Product findProductByName(String name);
    Product updateProduct(Long id, Product ProductDTO);
    void deleteById(Long id);
}
