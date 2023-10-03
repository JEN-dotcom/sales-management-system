package com.ingryd.sms.repository;

import com.ingryd.sms.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
 
}
