package com.ingryd.sms.service;

import com.ingryd.sms.entity.Product;
import com.ingryd.sms.model.ProductDTO;
import com.ingryd.sms.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product updatedProduct = productRepository.findById(id).orElseThrow();
        updatedProduct.setName(product.getName());
        updatedProduct.setCategory(product.getCategory());
        updatedProduct.setBrand(product.getBrand());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setDescription(product.getDescription());
        updatedProduct.setStock(product.getStock());
        updatedProduct.setDiscount(product.getDiscount());

        productRepository.save(updatedProduct);
        return updatedProduct;
    }

    @Override
    public ResponseEntity<String> deleteProduct(Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.ok("Product successfully deleted");
    }
}
