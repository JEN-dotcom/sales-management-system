//package com.ingryd.sms.service;
//
//import com.ingryd.sms.entity.*;
//import com.ingryd.sms.error.ObjectNotFoundException;
//import com.ingryd.sms.model.OrderItemDTO;
//import com.ingryd.sms.repository.*;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//<<<<<<< HEAD
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//=======
//import org.mockito.junit.jupiter.MockitoExtension;
//>>>>>>> main
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//<<<<<<< HEAD
//import java.io.File;
//import java.io.IOException;
//=======
//>>>>>>> main
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.ZoneId;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class OrderServiceTest {
//
//    @InjectMocks
//    private OrderServiceImpl orderService;
//
//    @Mock
//    private OrderRepository orderRepository;
//
//    @Mock
//    private OrderItemService orderItemService;
//
//    @Mock
//    private InvoiceService invoiceService;
//
//<<<<<<< HEAD
//    @InjectMocks
//    private OrderServiceImpl orderService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//
//    @Test
//    public void testCreateOrder() throws IOException {
//
//        Order createdOrder = new Order();
//        Invoice invoice = new Invoice();
//        invoice.setOrder(createdOrder);
//        invoice.setDate(new Date());
//
//        ClassLoader classLoader = getClass().getClassLoader();
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        File orderItemOneFile = new File(Objects.requireNonNull(classLoader.getResource("data/orderItemOne.json")).getFile());
//        OrderItemDTO[] orderItemOneDTO = objectMapper.readValue(orderItemOneFile, OrderItemDTO[].class);
//        List<OrderItemDTO> orderItemsOneDTOList = new ArrayList<>(Arrays.asList(orderItemOneDTO));
//
//        System.out.println(orderItemsOneDTOList);
//        List<OrderItem> orderItemsList = new ArrayList<>();
//
////        when(productRepository.findByNameAndBrand(any(String.class), any(String.class))).thenReturn((new Product(1L,"Rice", 350000.00, "naija", 100, "Aba", "local", 25)));
//
//        for (OrderItemDTO orderItemDTO : orderItemsOneDTOList) {
//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrder(createdOrder);
//            orderItem.setProduct(productRepository.findByNameAndBrand(orderItemDTO.getName(), orderItemDTO.getBrand()).get());
//            orderItem.setQuantity(orderItemDTO.getQuantity());
//
//            orderItemsList.add(orderItem);
//
//        }
//        createdOrder.setOrderId(1L);
//        createdOrder.setOrderItems(orderItemsList);
//        createdOrder.setInvoice(invoice);
//        createdOrder.setUser(new User(1L, "Efe", "Okorobie", "eokoro@gmail.com", "55757577573"));
//        createdOrder.setDate(new Date());
//
//        when(invoiceService.createInvoice(any(Date.class), any(Order.class))).thenReturn(invoice);
//        when(orderItemService.createOrderItem(anyList(), any(Order.class))).thenReturn(orderItemsList);
//        when(orderRepository.save(any(Order.class))).thenReturn(createdOrder);
//
//        Order resultOrder = orderService.createOrder(userRepository.findById(1L).get(), orderItemsOneDTOList);
//
//        assertNotNull(resultOrder);
//        assertEquals(userRepository.findById(1L).get(), resultOrder.getUser());
//    }
//
//=======
//>>>>>>> main
//    @Test
//    public void testGetAllOrdersPaginated() {
//        int pageNumber = 0;
//        int pageSize = 10;
//
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        List<Order> sampleOrders = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            sampleOrders.add(new Order());
//        }
//<<<<<<< HEAD
//        Page<Order> samplePage = new PageImpl<>(sampleOrders, pageable, sampleOrders.size());
//=======
//        Page<Order> samplePage = new PageImpl<>(sampleOrders, pageable,
//                sampleOrders.size());
//>>>>>>> main
//
//        when(orderRepository.findAll(any(Pageable.class))).thenReturn(samplePage);
//
//        List<Order> orders = orderService.getAllOrdersPaginated(pageNumber,
//                pageSize);
//
//        assertNotNull(orders);
//        assertEquals(sampleOrders.size(), orders.size());
//    }
//
//    @Test
//<<<<<<< HEAD
//    public void testGetOrderById(){
//        long orderId = 1L;
//        Order order = new Order();
//        order.setOrderId(orderId);
//
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
//
//        Order result = orderService.getOrderById(orderId);
//
//        assertNotNull(result);
//        assertEquals(orderId, result.getOrderId().longValue());
//    }
//
//    @Test
//    public void testGetOrdersByDatePaginated() throws ParseException {
//        String dateStringDDMMYYYY = "11-10-2023";
//        Date specificDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateStringDDMMYYYY);
//        LocalDateTime localDateTime = specificDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//
//        LocalDateTime startDate = localDateTime.with(LocalTime.MIN);
//        LocalDateTime endDate = localDateTime.with(LocalTime.MAX);
//
//        Date startOfDay = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
//        Date endOfDay = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());
//
//        int page = 0;
//        int pageSize = 10;
//
//        PageRequest pageRequest = PageRequest.of(page, pageSize);
//
//        List<Order> orders = new ArrayList<>();
//
////        when(orderRepository.findByDateBetween(startOfDay, endOfDay, pageRequest)).thenReturn(
////                new PageImpl<>(orders, pageRequest, orders.size()));
//
//        when(orderRepository.findByDateBetween(startOfDay, endOfDay, pageRequest)).thenReturn(
//                new ArrayList<>());
//        List<Order> result = orderService.getOrdersByDatePaginated(dateStringDDMMYYYY, page, pageSize);
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//    }
//
//
////    @Test
////    public void testGetOrdersByDatePaginated() throws ParseException {
////        String startDateStringDDMMYYYY = "11-10-2023";
////        String endDateStringDDMMYYYY = "12-10-2023";
////
////        Date startOfDay = new SimpleDateFormat("dd-MM-yyyy").parse(startDateStringDDMMYYYY);
////        Date endOfDay = new SimpleDateFormat("dd-MM-yyyy").parse(endDateStringDDMMYYYY);
////
////        int page = 0;
////        int pageSize = 10;
////
////        PageRequest pageRequest = PageRequest.of(page, pageSize);
////
////        List<Order> orders = new ArrayList<>();
////        Date orderDateWithinRange = new SimpleDateFormat("dd-MM-yyyy").parse("11-10-2023");
////        Order order1 = new Order();
////        order1.setDate(orderDateWithinRange);
////        orders.add(order1);
////
////        when(orderRepository.findByDateBetween(startOfDay, endOfDay, pageRequest))
////                .thenReturn(new PageImpl<>(orders, pageRequest, orders.size()));
////
////        List<Order> result = orderService.getOrdersByDatePaginated(startDateStringDDMMYYYY, endDateStringDDMMYYYY, page, pageSize);
////
////        assertNotNull(result);
////        assertTrue(result.contains(order1));
////    }
//=======
//    public void createOrder_Success() {
//        User user = new User();
//        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
//>>>>>>> main
//
//        Order order = new Order();
//        Invoice invoice = new Invoice();
//
//        when(orderRepository.save(any(Order.class))).thenReturn(order);
//        when(orderItemService.createOrderItem(eq(orderItemDTOList), eq(order))).thenReturn(order.getOrderItems());
//        when(invoiceService.createInvoice(any(Date.class), eq(order))).thenReturn(invoice);
//
//        Order createdOrder = orderService.createOrder(user, orderItemDTOList);
//
//        verify(orderRepository, times(3)).save(any(Order.class));
//
//        assertNotNull(createdOrder);
//    }
//
//    @Test
//    public void getOrderById_Exists() {
//        Long orderId = 1L;
//
//        Order order = new Order();
//        order.setOrderId(orderId);
//
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
//
//        Order result = orderService.getOrderById(orderId);
//
//        verify(orderRepository, times(1)).findById(orderId);
//
//        assertNotNull(result);
//        assertEquals(orderId, result.getOrderId());
//    }
//
//    @Test
//    public void getOrderById_NotFound() {
//        Long orderId = 1L;
//
//        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
//
//        assertThrows(ObjectNotFoundException.class, () -> orderService.getOrderById(orderId));
//
//        verify(orderRepository, times(1)).findById(orderId);
//    }
//
//    @Test
//    public void getOrdersByDatePaginated_SingleDate() throws ParseException {
//        String dateStringDDMMYYYY = "01-01-2023";
//        int page = 0;
//        int pageSize = 10;
//
//        Order order = new Order();
//
//        Date specificDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateStringDDMMYYYY);
//        LocalDateTime dateTime = specificDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//        LocalDateTime endDate = dateTime.with(LocalTime.MAX);
//        Date endOfDay = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());
//
//        Pageable pageRequest = PageRequest.of(page, pageSize);
//        when(orderRepository.findByDateBetween(specificDate, endOfDay, pageRequest))
//                .thenReturn(Collections.singletonList(order));
//
//        List<Order> result = orderService.getOrdersByDatePaginated(dateStringDDMMYYYY, page, pageSize);
//
//        verify(orderRepository, times(1)).findByDateBetween(specificDate, endOfDay, pageRequest);
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//    }
//
//    @Test
//    public void getOrdersByDatePaginated_DateRange() throws ParseException {
//        String startDateStringDDMMYYYY = "01-01-2023";
//        String endDateStringDDMMYYYY = "02-01-2023";
//        int page = 0;
//        int pageSize = 10;
//
//        Order order1 = new Order();
//        Order order2 = new Order();
//
//        Date startOfDay = new SimpleDateFormat("dd-MM-yyyy").parse(startDateStringDDMMYYYY);
//        Date endOfDay = new SimpleDateFormat("dd-MM-yyyy").parse(endDateStringDDMMYYYY);
//
//        LocalDateTime endOfEndDate = endOfDay.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
//                .with(LocalTime.MAX);
//        endOfDay = Date.from(endOfEndDate.atZone(ZoneId.systemDefault()).toInstant());
//
//        PageRequest pageRequest = PageRequest.of(page, pageSize);
//        when(orderRepository.findByDateBetween(startOfDay, endOfDay, pageRequest))
//                .thenReturn(Arrays.asList(order1, order2));
//
//        List<Order> result = orderService.getOrdersByDatePaginated(startDateStringDDMMYYYY, endDateStringDDMMYYYY, page,
//                pageSize);
//
//        verify(orderRepository, times(1)).findByDateBetween(startOfDay, endOfDay, pageRequest);
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//    }
//}
