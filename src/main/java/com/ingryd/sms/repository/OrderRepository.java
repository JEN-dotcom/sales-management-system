package com.ingryd.sms.repository;

import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findOrderById(long id);
}
