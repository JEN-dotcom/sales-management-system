package com.ingryd.sms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    // @BeforeEach
    // void setUp() {
    //     User user = User.builder()
    //             .firstName("Efe")
    //             .lastName("Okorobie")
    //             .email("eokoro@gmail.com")
    //             .build();

    //     userRepository.save(user);
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
    public void findAllPagination() {
       

        Pageable firstPageWithThreeRecords = PageRequest.of(0, 3);

        Pageable secondPageWithTwoRecords = PageRequest.of(1, 2);

        // List<Order> twoOrders = orderRepository.findAll(secondPageWithTwoRecords).getContent();

        // List<Order> threeOrders = orderRepository.findAll(firstPageWithThreeRecords).getContent();

        long twoElements = orderRepository.findAll(secondPageWithTwoRecords).getTotalElements();
        long threeElements = orderRepository.findAll(secondPageWithTwoRecords).getTotalElements();


        int twoPages = orderRepository.findAll(firstPageWithThreeRecords).getTotalPages();       
        int threePages = orderRepository.findAll(firstPageWithThreeRecords).getTotalPages();


        assertEquals(twoElements, 1L);
        assertEquals(threeElements, 1L);
        assertEquals(twoPages, 1);
        assertEquals(threePages, 1);
       

        // assertEquals(department.getDepartmentName(), "Mechanical Engineering");
        // System.out.println("totalPages = " + totalPages);
        // System.out.println("totalElements = " + totalElements);
        // System.out.println("courses = " + courses);

    }
}
