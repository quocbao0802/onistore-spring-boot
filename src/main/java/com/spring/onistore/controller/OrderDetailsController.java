package com.spring.onistore.controller;


import com.spring.onistore.dto.OrderDetailsDto;
import com.spring.onistore.dto.ProductCheckOutDto;
import com.spring.onistore.entity.Order;
import com.spring.onistore.entity.OrderDetails;
import com.spring.onistore.entity.Product;
import com.spring.onistore.entity.User;
import com.spring.onistore.repository.OrderDetailsRepository;
import com.spring.onistore.repository.OrderRepository;
import com.spring.onistore.repository.ProductRepository;
import com.spring.onistore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/checkout")
public class OrderDetailsController {

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;


    @PostMapping("")
    public ResponseEntity<List<OrderDetails>> checkOut(@Validated @RequestBody OrderDetailsDto orderDetailsDto) {
        Long id;
        String username;
        Order order;
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        if (orderDetailsDto.getProductCheckOutDtoList().isEmpty() || username.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        User user = userRepository.findByUserName(username);

        order = new Order();
        order.setName(orderDetailsDto.getName());
        order.setEmail(orderDetailsDto.getEmail());
        order.setPhone(orderDetailsDto.getPhone());
        order.setAddress(orderDetailsDto.getAddress());
        order.setNote(orderDetailsDto.getNote());
        order.setUser(user);
        orderRepository.save(order);


        for(ProductCheckOutDto productCheckOutDto : orderDetailsDto.getProductCheckOutDtoList() ){
            if(productCheckOutDto.getAmount() > productCheckOutDto.getProductCustomDto().getQuantity()   ){
                return ResponseEntity.unprocessableEntity().build();
            }else{
                Product p = productRepository.getOne(productCheckOutDto.getProductCustomDto().getId());
                p.setQuantity(productCheckOutDto.getProductCustomDto().getQuantity() - productCheckOutDto.getAmount());
                productRepository.save(p);
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setProduct(productRepository.getOne(productCheckOutDto.getProductCustomDto().getId()));
                orderDetails.setOrder(order);
                orderDetails.setPrice((productRepository.findById(productCheckOutDto.getProductCustomDto().getId())).get().getPrice());
                orderDetails.setQuantity(productCheckOutDto.getAmount());
                orderDetailsList.add(orderDetails);
            }
        }

        List<OrderDetails> detailsList = orderDetailsRepository.saveAll(orderDetailsList);

        return ResponseEntity.ok().body(detailsList);
    }


}
