package com.spring.onistore.controller;

import com.spring.onistore.entity.Comment;
import com.spring.onistore.entity.Product;
import com.spring.onistore.entity.User;
import com.spring.onistore.repository.CommentRespository;
import com.spring.onistore.repository.ProductRepository;
import com.spring.onistore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/comments")
public class CommentController {

    @Autowired
    private CommentRespository commentRespository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public List<Comment> getAllComments() {
        return commentRespository.findAll();
    }

    @PostMapping("")
    public ResponseEntity<Comment> createIComments(@Validated @RequestBody Comment comment) {

        Optional<Product> optionalProduct = productRepository.findById(comment.getProduct().getId());
        Optional<User> optionalUser = userRepository.findById(comment.getUser().getId());

        if (optionalProduct.isEmpty() || optionalUser.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        comment.setProduct(optionalProduct.get());
        comment.setUser(optionalUser.get());
        Comment saveComment = commentRespository.save(comment);
        return ResponseEntity.ok().body(saveComment);
    }
}
