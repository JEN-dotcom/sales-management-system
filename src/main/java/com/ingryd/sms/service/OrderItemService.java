package com.ingryd.sms.service;

import java.util.List;

import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.OrderItem;
import com.ingryd.sms.model.OrderItemDTO;

public interface OrderItemService {
    
public List<OrderItem> createOrderItem(List<OrderItemDTO> orderItemDTOList, Order order);
    
}
