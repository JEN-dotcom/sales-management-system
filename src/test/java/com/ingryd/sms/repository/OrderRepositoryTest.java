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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ingryd.sms.entity.Order;

@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void saveOrder() {

        Order order = Order.builder()
                .user(userRepository.findById(1L).orElseThrow())
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
    public void findOrdersByDatePaginated() throws ParseException {
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
    @DisplayName("Find order by Id")
    public void whenFindById_thenReturnOrder() {
        Order found = orderRepository.findById(1L).get();
        assertEquals(1L, found.getOrderId());
    }

    @Test
    public void testDeleteByDateBefore() throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date cutoffDate = sdf.parse("01-01-2023 00:00:00");
        Date beforeCutoff = sdf.parse("31-12-2022 10:00:00");
        Date afterCutoff = sdf.parse("01-01-2023 10:00:00");

        Order order1 = createTestOrder(beforeCutoff);
        Order order2 = createTestOrder(afterCutoff);

        testEntityManager.persist(order1);
        testEntityManager.persist(order2);

        orderRepository.deleteByDateBefore(cutoffDate);

        Optional<Order> deletedOrder = orderRepository.findById(order1.getOrderId());
        assertTrue(deletedOrder.isEmpty());
    }

    private Order createTestOrder(Date date) {
        Order order = new Order();
        order.setUser(userRepository.findById(1L).orElseThrow());
        order.setDate(date);
        return order;
    }
}
