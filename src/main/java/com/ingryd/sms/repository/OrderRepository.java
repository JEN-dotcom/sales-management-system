package com.ingryd.sms.repository;

import com.ingryd.sms.entity.Order;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByDateBetween(Date startOfDay, Date endOfDay, Pageable pageable);
}
