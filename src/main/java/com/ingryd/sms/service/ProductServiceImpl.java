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
        Product product = Product.builder()
                .brand(productDTO.getBrand())
                .category(productDTO.getCategory())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .stock(productDTO.getStock())
                .discount(productDTO.getDiscount())
                .name(productDTO.getName())
                .build();
        return productRepository.save(
                product);
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
    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product updatedProduct = productRepository.findById(id).orElseThrow();
        updatedProduct.setName(productDTO.getName());
        updatedProduct.setCategory(productDTO.getCategory());
        updatedProduct.setBrand(productDTO.getBrand());
        updatedProduct.setPrice(productDTO.getPrice());
        updatedProduct.setDescription(productDTO.getDescription());
        updatedProduct.setStock(productDTO.getStock());
        updatedProduct.setDiscount(productDTO.getDiscount());

        return productRepository.save(updatedProduct);

    }

    @Override
    public ResponseEntity<String> deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.ok("Product successfully deleted");
        }
            return ResponseEntity.notFound().build();
    }
}
