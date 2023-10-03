package com.ingryd.sms.model;

import java.util.Date;
import java.util.List;

import com.ingryd.sms.entity.OrderItem;

public class OrderDTO {

    private Long userId;

    private Date date;

    private List<OrderItem> orderItems;
    
}
