package com.ingryd.sms.service;

import com.ingryd.sms.entity.Product;
import com.ingryd.sms.model.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    public List<Product> getAllProducts();
    public Product getProductById(long id);

    public Product getProductByCategory(String category);

    public Product getProductByName(String name);

    public Product createProduct(ProductDTO productDTO);

    public Product updateProduct(long id, ProductDTO productDTO);

    public ResponseEntity<String> deleteProduct(long id);
}