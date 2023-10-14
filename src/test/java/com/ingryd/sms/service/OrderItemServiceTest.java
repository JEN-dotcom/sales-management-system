package com.ingryd.sms.service;

import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.OrderItem;
import com.ingryd.sms.model.OrderItemDTO;
import com.ingryd.sms.repository.OrderItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = OrderItemServiceTest.class)
public class OrderItemServiceTest {

    @MockBean
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderItemService orderItemService;

    @Test
    public void testCreateOrderItem() {
        Order order = new Order();

        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
        orderItemDTOList.add(new OrderItemDTO());

        List<OrderItem> expectedOrderItems = new ArrayList<>();
        expectedOrderItems.add(new OrderItem());

        when(orderItemRepository.saveAll(any())).thenReturn(expectedOrderItems);

        List<OrderItem> createdOrderItems = orderItemService.createOrderItem(orderItemDTOList, order);

        verify(orderItemRepository, times(1)).saveAll(anyList());
        assertEquals(expectedOrderItems, createdOrderItems);
    }

}
