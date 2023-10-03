package com.ingryd.sms.repository;

import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Product findOrderById(long id);
}
