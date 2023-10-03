package com.ingryd.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingryd.sms.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
