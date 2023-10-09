package com.ingryd.sms.service;

import com.ingryd.sms.entity.Invoice;
import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.OrderItem;
import com.ingryd.sms.entity.User;
import com.ingryd.sms.model.OrderItemDTO;
import com.ingryd.sms.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderServiceImpl.class)
public class OrderServiceTest {

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private OrderItemService orderItemService;

    @Autowired
    private InvoiceService invoiceService;
    @Mock
    private OrderServiceImpl orderService;

//    @Test
//    public void testCreateOrder() {
//        User user = new User(1L, "Efe", "Okorobie", "efe@gmail.com", "55757577573");
//
//        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
//        orderItemDTOList.add(new OrderItemDTO(/* initialize DTO */));
//
//        Order expectedOrder = new Order(/* initialize order */);
//
//        when(invoiceService.createInvoice(any(Date.class), any(Order.class))).thenReturn(/* initialize invoice */);
//
//        when(orderItemService.createOrderItem(anyList(), any(Order.class))).thenReturn(/* initialize order items */);
//
//        Order createdOrder = orderService.createOrder(user, orderItemDTOList);
//
//        verify(orderRepository, times(1)).save(any(Order.class));
//
//        assertNotNull(createdOrder);
//        assertEquals(user, createdOrder.getUser());
//    }
//
//
//    @Test
//    public void testCreateOrder() {
//        User user = new User(1L, "Efe", "Okorobie", "efe@gmail.com", "55757577573");
//        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
//
//        Order createdOrder = new Order();
//
//        when(invoiceService.createInvoice(any(Date.class), any(Order.class))).thenReturn();
//        when(orderItemService.createOrderItem(anyList(), any(Order.class))).thenReturn();
//        when(orderRepository.save(any(Order.class))).thenReturn(createdOrder);
//
//        Order resultOrder = orderService.createOrder(user, orderItemDTOList);
//
//        verify(invoiceService, times(1)).createInvoice(any(Date.class), any(Order.class));
//        verify(orderItemService, times(1)).createOrderItem(anyList(), any(Order.class));
//        verify(orderRepository, times(1)).save(any(Order.class));
//
//        assertNotNull(resultOrder);
//        assertEquals(user, resultOrder.getUser());
//    }

}
