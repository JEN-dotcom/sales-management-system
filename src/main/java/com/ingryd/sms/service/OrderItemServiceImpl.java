package com.ingryd.sms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.OrderItem;
import com.ingryd.sms.entity.Product;
import com.ingryd.sms.model.OrderItemDTO;
import com.ingryd.sms.repository.OrderItemRepository;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    ProductService productService;

    @Override
    public List<OrderItem> createOrderItem(List<OrderItemDTO> orderItemDTOList, Order order) {
        List<OrderItem> orderItemsList = new ArrayList<>();

        for (OrderItemDTO orderItemDTO : orderItemDTOList) {
            OrderItem orderItem = new OrderItem();
            Product product = productService.getProductByNameAndBrand(orderItemDTO.getName(), orderItemDTO.getBrand());

            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setOrder(order);
            orderItemsList.add(orderItem);

            productService.updateProduct(product, orderItemDTO.getQuantity());
        }
        return orderItemRepository.saveAll(orderItemsList);
    }
}