package com.spring.onistore.controller;

import com.spring.onistore.entity.Order;
import com.spring.onistore.exception.ResourceNotFoundException;
import com.spring.onistore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/orders")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOrderById(@PathVariable(value = "id") Long orderId)
            throws ResourceNotFoundException {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            Map<String, String> map = new HashMap<>();
            map.put("message", "Order not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        } else {
            return ResponseEntity.ok().body(order.get());
        }
    }


}
