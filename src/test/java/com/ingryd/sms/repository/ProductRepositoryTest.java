package com.ingryd.sms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.ingryd.sms.entity.Product;
import com.ingryd.sms.model.ProductDTO;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    private ProductDTO[] productDTOs;

    @BeforeEach
    void setUp() throws StreamReadException, DatabindException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        ObjectMapper objectMapper = new ObjectMapper();

        File productsFile = new File(classLoader.getResource("data/products.json").getFile());
        productDTOs = objectMapper.readValue(productsFile, ProductDTO[].class);
    }

    @Test
    public void saveProductTest() {
        Product product = createProduct(productDTOs[0]);
        product.setName("Rice");

        Product savedProduct = productRepository.save(product);

        assertEquals("Rice", savedProduct.getName());
    }

    @Test
    public void findById() {

        for (ProductDTO productDTO : productDTOs) {
            entityManager.persist(createProduct(productDTO));
        }

        Product found = productRepository.findById(1L).get();
        assertEquals(1L, found.getId());
    }

    @Test
    public void findByCategory() {

        int categoryCount = 0;
        for (ProductDTO productDTO : productDTOs) {
            if ("Electronics".equals(productDTO.getCategory())) {
                categoryCount++;
            }
            entityManager.persist(createProduct(productDTO));
        }
        List<Product> found = productRepository.findByCategory("Electronics");
        assertEquals(categoryCount, found.size());
    }

    @Test
    public void findByName() {
        int nameCount = 0;
        for (ProductDTO productDTO : productDTOs) {
            if ("Sony PlayStation 4".equals(productDTO.getName())) {
                nameCount++;
            }
            entityManager.persist(createProduct(productDTO));
        }
        List<Product> found = productRepository.findByName("Sony PlayStation 4");
        assertEquals(nameCount, found.size());
    }

    @Test
    public void deleteById() {
        for (ProductDTO productDTO : productDTOs) {
            entityManager.persist(createProduct(productDTO));
        }

        List<Product> found = productRepository.findAll();
        Long id = found.get(0).getId();

        productRepository.deleteById(id);

        Optional<Product> deletedProduct = productRepository.findById(id);
        assertTrue(deletedProduct.isEmpty());        
    }

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
        return product;
    }

}
