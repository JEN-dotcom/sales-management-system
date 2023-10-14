package com.ingryd.sms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.OrderItem;
import com.ingryd.sms.entity.Product;
import com.ingryd.sms.entity.User;
import com.ingryd.sms.model.ProductDTO;

@DataJpaTest
public class OrderItemRepositoryTest {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User user1;

    @BeforeEach
    void setUp() throws StreamReadException, DatabindException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        ObjectMapper objectMapper = new ObjectMapper();

        File productsFile = new File(classLoader.getResource("data/products.json").getFile());
        ProductDTO[] productDTOs = objectMapper.readValue(productsFile, ProductDTO[].class);

        for (ProductDTO productDTO : productDTOs) {
            entityManager.persist(createProduct(productDTO));
        }

        user1 = User.builder()
                .firstName("Efe")
                .lastName("Okorobie")
                .email("eokoro@gmail.com")
                .build();

        user1 = entityManager.persistAndFlush(user1);
    }

    @Test
    public void saveOrderItemList() {
        Order order = new Order();
        order.setUser(user1);

        List<OrderItem> orderItemsList = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(productRepository.findById(1L).orElseThrow());
        orderItemsList.add(orderItem);

        orderItemsList = orderItemRepository.saveAll(orderItemsList);

        assertEquals(orderItemsList.get(0).getProduct(), productRepository.findById(1L).get());
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
