package com.ingryd.sms.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.User;

import jakarta.transaction.Transactional;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User user1;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .firstName("Efe")
                .lastName("Okorobie")
                .email("eokoro@gmail.com")
                .build();

        user1 = entityManager.persistAndFlush(user1);
    }

    @Test
    public void saveOrder() {
        Order order = Order.builder()
                .user(user1)
                .build();

        orderRepository.save(order);
    }

    @Test
    public void findAllOrdersPaginated() {
        entityManager.persist(createTestOrder());
        entityManager.persist(createTestOrder());
        entityManager.persist(createTestOrder());
        entityManager.persist(createTestOrder());

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
    public void findOrdersByDatePaginated() throws ParseException {
        entityManager.persist(createTestOrder());
        entityManager.persist(createTestOrder());
        entityManager.persist(createTestOrder());
        entityManager.persist(createTestOrder());

        LocalDate today = LocalDate.now();

        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        Date startofDay = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
        Date endofDay = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

        Pageable firstPageWithThreeRecords = PageRequest.of(0, 3);
        Pageable secondPageWithTwoRecords = PageRequest.of(1, 2);

        List<Order> threeOrders = orderRepository.findByDateBetween(startofDay, endofDay, firstPageWithThreeRecords);
        assertEquals(3, threeOrders.size());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = dateFormat.parse("01-01-2050");
        Date endDate = dateFormat.parse("01-01-2051");

        List<Order> noOrders = orderRepository.findByDateBetween(startDate, endDate, secondPageWithTwoRecords);
        assertEquals(0, noOrders.size());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date yesterday = calendar.getTime();

        List<Order> twoOrders = orderRepository.findByDateBetween(yesterday, endofDay, secondPageWithTwoRecords);

        assertEquals(2, twoOrders.size());
    }

    @Test
    public void findById() {
        Date date = new Date();
        Order order1 = createTestOrder(date);
        entityManager.persist(order1);
        Order found = orderRepository.findById(order1.getOrderId()).get();
        assertEquals(order1.getOrderId(), found.getOrderId());
    }

    @Test
    @Transactional
    public void testDeleteByDateBefore() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date cutoffDate = sdf.parse("01-01-2023 00:00:00");
        Date beforeCutoff = sdf.parse("31-12-2022 10:00:00");
        Date afterCutoff = sdf.parse("01-01-2023 10:00:00");

        Order order1 = createTestOrder(beforeCutoff);
        Order order2 = createTestOrder(afterCutoff);

        entityManager.persist(order1);
        entityManager.persist(order2);

        orderRepository.deleteByDateBefore(cutoffDate);

        Optional<Order> deletedOrder = orderRepository.findById(order1.getOrderId());
        assertTrue(deletedOrder.isEmpty());
    }

    private Order createTestOrder(Date date) {
        Order order = createTestOrder();
        order.setDate(date);
        return order;
    }

    private Order createTestOrder() {
        Date date = new Date();
        Order order = new Order();
        order.setUser(user1);
        order.setDate(date);
        return order;
    }
}
