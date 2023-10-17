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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        order.setOrderItems(orderItemService.createOrderItem(orderItemDTOList, orderRepository.save(order)));
        order.setInvoice(invoiceService.createInvoice(date,  orderRepository.save(order)));

        return orderRepository.save(order);
    }
    @Override
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
        LocalDateTime endDate = dateTime.with(LocalTime.MAX);
        Date endofDay = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());

        Pageable pageRequest = PageRequest.of(page, pageSize);
        return orderRepository.findByDateBetween(specificDate, endofDay, pageRequest);
    }

    @Override
    public List<Order> getOrdersByDatePaginated(String startDateStringDDMMYYYY, String endDateStringDDMMYYYY, int page,
            int pageSize) throws ParseException {
        Date startOfDay = new SimpleDateFormat("dd-MM-yyyy").parse(startDateStringDDMMYYYY);
        Date endOfDay = new SimpleDateFormat("dd-MM-yyyy").parse(endDateStringDDMMYYYY);

        LocalDateTime endOfDate = endOfDay.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                .with(LocalTime.MAX);
        endOfDay = Date.from(endOfDate.atZone(ZoneId.systemDefault()).toInstant());

        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return orderRepository.findByDateBetween(startOfDay, endOfDay, pageRequest);
    }

    @Override
    public Order getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Order does not exist"));

        return order;
    }

    @Override
    public ResponseEntity<String> deleteOrdersBeforeDate(String dateStringDDMMYYYY) throws ParseException {
        Date cutoffDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateStringDDMMYYYY);
        try {
            orderRepository.deleteByDateBefore(cutoffDate);
            return ResponseEntity.ok("All entries before " + dateStringDDMMYYYY + " successfully deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting entries.");
        }
    }
}