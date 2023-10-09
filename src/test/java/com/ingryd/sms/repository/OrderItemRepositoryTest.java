package com.ingryd.sms.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.OrderItem;

@SpringBootTest
public class OrderItemRepositoryTest {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    public void saveOrderItemList() {
        Order order = new Order();
        order.setUser(userRepository.findById(1L).orElseThrow());
        orderRepository.save(order);

        List<OrderItem> orderItemsList = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(productRepository.findById(1L).orElseThrow());
        orderItemsList.add(orderItem);

        orderItemRepository.saveAll(orderItemsList);
    }
}
