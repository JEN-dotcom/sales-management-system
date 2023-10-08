package com.ingryd.sms.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.User;
import com.ingryd.sms.error.ObjectNotFoundException;
import com.ingryd.sms.model.OrderItemDTO;
import com.ingryd.sms.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    InvoiceService invoiceService;

    @Override
    @Transactional
    public Order createOrder(User user, List<OrderItemDTO> orderItemDTOList) {
        Date date = new Date();
        Order order = new Order();

        order.setUser(user);
        order.setDate(date);
        order.setInvoice(invoiceService.createInvoice(date, order));
        order.setOrderItems(orderItemService.createOrderItem(orderItemDTOList, order));

        orderRepository.save(order);

        return order;
    }

    public List<Order> getAllOrdersPaginated(int pageNumber, int pageSize) {
        Pageable pageWithRecords = PageRequest.of(pageNumber, pageSize);

        List<Order> orders = orderRepository.findAll(pageWithRecords).getContent();

        return orders;
    }

    @Override
    public List<Order> getOrdersByDatePaginated(String dateStringDDMMYYYY, int page, int pageSize)
            throws ParseException {
        Date specificDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateStringDDMMYYYY);
        LocalDateTime dateTime = specificDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        LocalDateTime startDate = dateTime.with(LocalTime.MIN);
        LocalDateTime endDate = dateTime.with(LocalTime.MAX);

        Date startOfDay = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
        Date endofDay = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());

        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return orderRepository.findByDateBetween(startOfDay, endofDay, pageRequest);
    }

    @Override
    public List<Order> getOrdersByDatePaginated(String startDateStringDDMMYYYY, String endDateStringDDMMYYYY, int page,
            int pageSize) throws ParseException {
        Date startOfDay = new SimpleDateFormat("dd-MM-yyyy").parse(startDateStringDDMMYYYY);
        Date endOfDay = new SimpleDateFormat("dd-MM-yyyy").parse(endDateStringDDMMYYYY);

        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return orderRepository.findByDateBetween(startOfDay, endOfDay, pageRequest);
    }

    @Override
    public Order getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Order does not exist"));

        return order;
    }
}