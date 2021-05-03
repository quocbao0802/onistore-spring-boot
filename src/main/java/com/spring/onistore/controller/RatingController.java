package com.spring.onistore.controller;

import com.spring.onistore.entity.Product;
import com.spring.onistore.entity.Rating;
import com.spring.onistore.entity.User;
import com.spring.onistore.repository.ProductRepository;
import com.spring.onistore.repository.RatingRepository;
import com.spring.onistore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/ratings")
public class RatingController {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public List<Rating> getAllRating() {
        return ratingRepository.findAll();
    }

    @PostMapping("")
    public ResponseEntity<Rating> createIComments(@Validated @RequestBody Rating rating) {

        Optional<Product> optionalProduct = productRepository.findById(rating.getProduct().getId());
        Optional<User> optionalUser = userRepository.findById(rating.getUser().getId());

        if (optionalProduct.isEmpty() || optionalUser.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        rating.setProduct(optionalProduct.get());
        rating.setUser(optionalUser.get());
        Rating saveRating = ratingRepository.save(rating);
        return ResponseEntity.ok().body(saveRating);
    }
}
