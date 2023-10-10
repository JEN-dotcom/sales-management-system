package com.ingryd.sms.service;

import java.text.ParseException;
import java.util.List;

import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.User;
import com.ingryd.sms.model.OrderItemDTO;

public interface OrderService {
    public Order createOrder(User user, List<OrderItemDTO> orderItemDTOList);

    public Order getOrderById(Long id);

    public List<Order> getAllOrdersPaginated(int pageNumber, int pageSize);

    public List<Order> getOrdersByDatePaginated(String dateStringDDMMYYYY, int page, int pageSize) throws ParseException;

    public List<Order> getOrdersByDatePaginated(String startDateStringDDMMYYYY, String endDateStringDDMMYYYY, int page, int pageSize) throws ParseException;

   // delete orders beyound a certain date
}
