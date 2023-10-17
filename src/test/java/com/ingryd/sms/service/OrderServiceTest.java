package com.ingryd.sms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ingryd.sms.SalesManagementSystemApplication;
import com.ingryd.sms.entity.*;
import com.ingryd.sms.model.OrderItemDTO;
import com.ingryd.sms.repository.*;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = SalesManagementSystemApplication.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemService orderItemService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private InvoiceService invoiceService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Autowired
    private UserRepository userRepository;



    @Test
    public void testCreateOrder() throws IOException {

        Order createdOrder = new Order();
        Invoice invoice = new Invoice();
        invoice.setOrder(createdOrder);
        invoice.setDate(new Date());

        ClassLoader classLoader = getClass().getClassLoader();
        ObjectMapper objectMapper = new ObjectMapper();

        File orderItemOneFile = new File(Objects.requireNonNull(classLoader.getResource("data/orderItemOne.json")).getFile());
        OrderItemDTO[] orderItemOneDTO = objectMapper.readValue(orderItemOneFile, OrderItemDTO[].class);
        List<OrderItemDTO> orderItemsOneDTOList = new ArrayList<>(Arrays.asList(orderItemOneDTO));

        System.out.println(orderItemsOneDTOList);
        List<OrderItem> orderItemsList = new ArrayList<>();

//        when(productRepository.findByNameAndBrand(any(String.class), any(String.class))).thenReturn((new Product(1L,"Rice", 350000.00, "naija", 100, "Aba", "local", 25)));

        for (OrderItemDTO orderItemDTO : orderItemsOneDTOList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(createdOrder);
            orderItem.setProduct(productRepository.findByNameAndBrand(orderItemDTO.getName(), orderItemDTO.getBrand()).get());
            orderItem.setQuantity(orderItemDTO.getQuantity());

            orderItemsList.add(orderItem);

        }
        createdOrder.setOrderId(1L);
        createdOrder.setOrderItems(orderItemsList);
        createdOrder.setInvoice(invoice);
        createdOrder.setUser(new User(1L, "Efe", "Okorobie", "eokoro@gmail.com", "55757577573"));
        createdOrder.setDate(new Date());

        when(invoiceService.createInvoice(any(Date.class), any(Order.class))).thenReturn(invoice);
        when(orderItemService.createOrderItem(anyList(), any(Order.class))).thenReturn(orderItemsList);
        when(orderRepository.save(any(Order.class))).thenReturn(createdOrder);

        Order resultOrder = orderService.createOrder(userRepository.findById(1L).get(), orderItemsOneDTOList);

        assertNotNull(resultOrder);
        assertEquals(userRepository.findById(1L).get(), resultOrder.getUser());
    }

    @Test
    public void testGetAllOrdersPaginated() {
        int pageNumber = 0;
        int pageSize = 10;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Order> sampleOrders = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            sampleOrders.add(new Order());
        }
        Page<Order> samplePage = new PageImpl<>(sampleOrders, pageable, sampleOrders.size());

        when(orderRepository.findAll(any(Pageable.class))).thenReturn(samplePage);

        List<Order> orders = orderService.getAllOrdersPaginated(pageNumber, pageSize);

        assertNotNull(orders);
        assertEquals(sampleOrders.size(), orders.size());
    }

    @Test
    public void testGetOrderById(){
        long orderId = 1L;
        Order order = new Order();
        order.setOrderId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Order result = orderService.getOrderById(orderId);

        assertNotNull(result);
        assertEquals(orderId, result.getOrderId().longValue());
    }

    @Test
    public void testGetOrdersByDatePaginated() throws ParseException {
        String dateStringDDMMYYYY = "11-10-2023";
        Date specificDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateStringDDMMYYYY);
        LocalDateTime localDateTime = specificDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        LocalDateTime startDate = localDateTime.with(LocalTime.MIN);
        LocalDateTime endDate = localDateTime.with(LocalTime.MAX);

        Date startOfDay = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());

        int page = 0;
        int pageSize = 10;

        PageRequest pageRequest = PageRequest.of(page, pageSize);

        List<Order> orders = new ArrayList<>();

//        when(orderRepository.findByDateBetween(startOfDay, endOfDay, pageRequest)).thenReturn(
//                new PageImpl<>(orders, pageRequest, orders.size()));

        when(orderRepository.findByDateBetween(startOfDay, endOfDay, pageRequest)).thenReturn(
                new ArrayList<>());
        List<Order> result = orderService.getOrdersByDatePaginated(dateStringDDMMYYYY, page, pageSize);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


//    @Test
//    public void testGetOrdersByDatePaginated() throws ParseException {
//        String startDateStringDDMMYYYY = "11-10-2023";
//        String endDateStringDDMMYYYY = "12-10-2023";
//
//        Date startOfDay = new SimpleDateFormat("dd-MM-yyyy").parse(startDateStringDDMMYYYY);
//        Date endOfDay = new SimpleDateFormat("dd-MM-yyyy").parse(endDateStringDDMMYYYY);
//
//        int page = 0;
//        int pageSize = 10;
//
//        PageRequest pageRequest = PageRequest.of(page, pageSize);
//
//        List<Order> orders = new ArrayList<>();
//        Date orderDateWithinRange = new SimpleDateFormat("dd-MM-yyyy").parse("11-10-2023");
//        Order order1 = new Order();
//        order1.setDate(orderDateWithinRange);
//        orders.add(order1);
//
//        when(orderRepository.findByDateBetween(startOfDay, endOfDay, pageRequest))
//                .thenReturn(new PageImpl<>(orders, pageRequest, orders.size()));
//
//        List<Order> result = orderService.getOrdersByDatePaginated(startDateStringDDMMYYYY, endDateStringDDMMYYYY, page, pageSize);
//
//        assertNotNull(result);
//        assertTrue(result.contains(order1));
//    }

}
