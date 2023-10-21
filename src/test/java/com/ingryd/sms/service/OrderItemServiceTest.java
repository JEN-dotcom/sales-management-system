package com.ingryd.sms.service;

import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.OrderItem;
import com.ingryd.sms.entity.Product;
import com.ingryd.sms.model.OrderItemDTO;
import com.ingryd.sms.repository.OrderItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceTest {
    
    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ProductServiceImpl productService;

    @Test
    public void createOrderItem() {
    
        Order order = new Order();
        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
        orderItemDTOList.add(OrderItemDTO.builder().name("Product1").brand("Brand1").quantity(2).build());
        orderItemDTOList.add(OrderItemDTO.builder().name("Product2").brand("Brand2").quantity(3).build());

        Product product1 = new Product();
        product1.setName("Product1");
        product1.setBrand("Brand1");
        product1.setStock(10); 

        Product product2 = new Product();
        product2.setName("Product2");
        product2.setBrand("Brand2");
        product2.setStock(5); 

        when(productService.getProductByNameAndBrand("Product1", "Brand1")).thenReturn(product1);
        when(productService.getProductByNameAndBrand("Product2", "Brand2")).thenReturn(product2);

        when(productService.updateProduct(product1, 2)).thenReturn(product1);
        when(productService.updateProduct(product2, 3)).thenReturn(product2);

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setProduct(product1);
        orderItem1.setQuantity(2);
        orderItem1.setOrder(order);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setProduct(product2);
        orderItem2.setQuantity(3);
        orderItem2.setOrder(order);

        List<OrderItem> expectedOrderItems = Arrays.asList(orderItem1, orderItem2);

        when(orderItemRepository.saveAll(expectedOrderItems)).thenReturn(expectedOrderItems);

        List<OrderItem> result = orderItemService.createOrderItem(orderItemDTOList, order);


        verify(productService, times(1)).getProductByNameAndBrand("Product1", "Brand1");
        verify(productService, times(1)).getProductByNameAndBrand("Product2", "Brand2");
        verify(productService, times(1)).updateProduct(product1, 2);
        verify(productService, times(1)).updateProduct(product2, 3);

        assertEquals(expectedOrderItems, result);
    }
}
