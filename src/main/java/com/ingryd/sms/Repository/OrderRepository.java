package com.ingryd.sms.repository;

import com.ingryd.sms.entity.Order;

import java.util.Date;
import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByDateBetween(Date startOfDay, Date endOfDay, PageRequest pageRequest);

 
}
