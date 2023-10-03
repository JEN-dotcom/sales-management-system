package com.ingryd.sms.service;

import java.util.List;


import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.User;
import com.ingryd.sms.model.OrderItemDTO;


public interface OrderService {
    public Order createOrder(User user, List<OrderItemDTO> orderItemDTOList);

}
