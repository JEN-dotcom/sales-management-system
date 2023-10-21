package com.ingryd.sms.service;

import com.ingryd.sms.entity.Product;
import com.ingryd.sms.model.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    public List<Product> getAllProducts();

    public Product getProductById(Long id);

    public List<Product> getProductByCategory(String category);

    public List<Product> getProductByName(String name);

    public Product createProduct(ProductDTO productDTO);

    public Product updateProduct(Long id, ProductDTO productDTO);

    public Product getProductByNameAndBrand(String name, String brand);

    public ResponseEntity<String> deleteProduct(Long id);
}