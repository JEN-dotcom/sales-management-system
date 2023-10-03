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
    public Product createProduct(ProductDTO productDTO) {
        return productRepository.save(new Product());
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findProductById(id);
    }

    @Override
    public Product getProductByCategory(String category) {
        return productRepository.findProductByCategory(category);
    }

    @Override
    public Product getProductByName(String name) {
        return productRepository.findProductByName(name);
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product updatedProduct = productRepository.findProductById(id);
        updatedProduct.setName(productDTO.getName());
        updatedProduct.setCategory(productDTO.getCategory());
        updatedProduct.setBrand(productDTO.getBrand());
        updatedProduct.setPrice(productDTO.getPrice());
        updatedProduct.setDescription(productDTO.getDescription());
        updatedProduct.setStock(productDTO.getStock());
        updatedProduct.setDiscount(productDTO.getDiscount());

        productRepository.save(updatedProduct);
        return updatedProduct;
    }

    @Override
    public ResponseEntity<String> deleteProduct(Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.ok("Product successfully deleted");
    }
}
