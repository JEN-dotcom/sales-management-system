package com.ingryd.sms.repository;

import com.ingryd.sms.entity.Order;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    Page<Order> findByDateBetween(String SpecificDateformatDDMMYYY, PageRequest pageRequest);
    

    Page<Order> findByDateBetween(String StartDateformatDDMMYYY, String endDateformatDDMMYYY, PageRequest pageRequest);

}
