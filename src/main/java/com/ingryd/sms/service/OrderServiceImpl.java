package com.ingryd.sms.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ingryd.sms.entity.Invoice;
import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.OrderItem;
import com.ingryd.sms.entity.User;
import com.ingryd.sms.model.OrderItemDTO;
import com.ingryd.sms.repository.InvoiceRepository;
import com.ingryd.sms.repository.OrderItemRepository;
import com.ingryd.sms.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Override
    @Transactional
    public Order createOrder(User user, List<OrderItemDTO> orderItemDTOList) {

        Order order = new Order();
        Date date = new Date();

        List<OrderItem> orderItemsList = new ArrayList<>();

        for (OrderItemDTO orderItemDTO : orderItemDTOList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(orderItemDTO.getProduct());
            orderItem.setQuantity(orderItemDTO.getQuantity());

            orderItemsList.add(orderItem);
        }

        orderItemRepository.saveAll(orderItemsList);

        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setInvoiceDate(date);

        invoiceRepository.save(invoice);

        order.setOrderItems(orderItemsList);
        order.setUser(user);
        order.setDate(date);
        order.setInvoice(invoice);

        orderRepository.save(order);

        return order;
    }

    public List<Order> getAllOrdersPaginated(int pageNumber, int pageSize ) {
        Pageable pageWithRecords = PageRequest.of(pageNumber, pageSize);     

        List<Order> orders = orderRepository.findAll(pageWithRecords).getContent();

        return orders;
    }

    @Override
    public List<Order> getOrdersByDatePaginated(String dateStringDDMMYYYY, int page, int pageSize) throws ParseException {
        Date specificDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateStringDDMMYYYY);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(specificDate);        
       
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startOfDay = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endOfDay = calendar.getTime();

        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return orderRepository.findByDateBetween(startOfDay, endOfDay, pageRequest);
    }

    @Override
    public List<Order> getOrdersByDatePaginated(String startDateStringDDMMYYYY, String endDateStringDDMMYYYY, int page, int pageSize) throws ParseException {
        Date startOfDay =new SimpleDateFormat("dd-MM-yyyy").parse(startDateStringDDMMYYYY);       
        Date endOfDay = new SimpleDateFormat("dd-MM-yyyy").parse(endDateStringDDMMYYYY);

        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return orderRepository.findByDateBetween(startOfDay, endOfDay, pageRequest);
    }

    // @Override
    // public Order getOrderById(Long id) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'getOrderById'");
    // }
}