package com.ingryd.sms.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ingryd.sms.entity.Invoice;
import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.OrderItem;
import com.ingryd.sms.entity.User;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = User.builder()
        .firstName("Efe")
        .lastName("Okorobie")
        .email("eokoro@gmail.com")
        .build();

        userRepository.save(user);
    }

    @Test
    @DisplayName("Save order with all necessary fields")
    public void saveOrderWithInvoiceAndUserAndListOfOrderItems() {

        Invoice invoice = new Invoice();

        List<OrderItem> orderItemsList = new ArrayList<>();

        Order order = Order.builder()
                .invoice(invoice)
                .user(userRepository.findById(1L).orElseThrow(() -> new RuntimeException("no user")))
                .orderItems(orderItemsList)
                .build();

        orderRepository.save(order);
    }
}
