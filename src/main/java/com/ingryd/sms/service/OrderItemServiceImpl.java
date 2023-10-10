package com.ingryd.sms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.OrderItem;
import com.ingryd.sms.error.ObjectNotFoundException;
import com.ingryd.sms.model.OrderItemDTO;
import com.ingryd.sms.repository.OrderItemRepository;
import com.ingryd.sms.repository.ProductRepository;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<OrderItem> createOrderItem(List<OrderItemDTO> orderItemDTOList, Order order) {
        List<OrderItem> orderItemsList = new ArrayList<>();

        for (OrderItemDTO orderItemDTO : orderItemDTOList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(productRepository.findByNameAndBrand(orderItemDTO.getName(), orderItemDTO.getBrand()));
            orderItem.setQuantity(orderItemDTO.getQuantity());


            orderItemsList.add(orderItem);
        }
        return orderItemRepository.saveAll(orderItemsList);
    }
}