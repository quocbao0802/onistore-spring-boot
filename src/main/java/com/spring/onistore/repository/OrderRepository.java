package com.spring.onistore.repository;

import com.spring.onistore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {



}
