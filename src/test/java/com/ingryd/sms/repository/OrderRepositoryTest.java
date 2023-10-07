package com.ingryd.sms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ingryd.sms.entity.Invoice;
import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.OrderItem;
import com.ingryd.sms.entity.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    // @BeforeEach
    // void setUp() {
    // User user = User.builder()
    // .firstName("Efe")
    // .lastName("Okorobie")
    // .email("eokoro@gmail.com")
    // .build();

    // userRepository.save(user);
    // }

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

    @Test
    public void findAllOrdersPaginated() {

        Pageable firstPageWithThreeRecords = PageRequest.of(0, 3);
        Pageable secondPageWithTwoRecords = PageRequest.of(1, 2);

        Page<Order> firstPage = orderRepository.findAll(firstPageWithThreeRecords);
        Page<Order> secondPage = orderRepository.findAll(secondPageWithTwoRecords);

        List<Order> twoOrders = secondPage.getContent();
        List<Order> threeOrders = firstPage.getContent();

        assertEquals(2, twoOrders.size());
        assertEquals(3, threeOrders.size());
    }

    @Test
    public void findByDatePagination() {
        LocalDate currentDate = LocalDate.now();

        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");

        // Format the date as a string
        String formattedDate = currentDate.format(formatter);

        Pageable firstPageWithThreeRecords = PageRequest.of(0, 3);
        Pageable secondPageWithTwoRecords = PageRequest.of(1, 2);

        Page<Order> firstPage = orderRepository.findByDateBetween(formattedDate, firstPageWithThreeRecords);
        Page<Order> secondPage = orderRepository.findAll(secondPageWithTwoRecords);

        List<Order> twoOrders = secondPage.getContent();
        List<Order> threeOrders = firstPage.getContent();

        assertEquals(2, twoOrders.size());
        assertEquals(3, threeOrders.size());
    }

}
