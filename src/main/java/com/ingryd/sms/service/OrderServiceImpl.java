package com.ingryd.sms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

        Invoice invoice  = new Invoice();
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

}