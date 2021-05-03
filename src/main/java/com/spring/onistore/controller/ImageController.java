package com.spring.onistore.controller;

import com.spring.onistore.entity.Image;
import com.spring.onistore.entity.Product;
import com.spring.onistore.exception.ResourceNotFoundException;
import com.spring.onistore.repository.ImageRepository;
import com.spring.onistore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/images")
public class ImageController {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    public List<Image> getAllImage() {
        return imageRepository.findAll();
    }

    @PostMapping("")
    public ResponseEntity<Image> createImage(@Validated @RequestBody Image image) {

        Optional<Product> optionalProduct = productRepository.findById(image.getProduct().getId());

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        image.setProduct(optionalProduct.get());
        Image saveImage = imageRepository.save(image);
        return ResponseEntity.ok().body(saveImage);
    }
}
