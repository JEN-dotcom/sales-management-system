package com.ingryd.sms.Repository;

import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Product findOrderById(long id);
}
