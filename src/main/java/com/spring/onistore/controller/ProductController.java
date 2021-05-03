package com.spring.onistore.controller;

import com.spring.onistore.dto.ProductCateDto;
import com.spring.onistore.entity.Category;
import com.spring.onistore.entity.Image;
import com.spring.onistore.entity.Product;
import com.spring.onistore.entity.ProductCategory;
import com.spring.onistore.exception.ResourceNotFoundException;
import com.spring.onistore.repository.CategoryProductRepository;
import com.spring.onistore.repository.CategoryRepository;
import com.spring.onistore.repository.ImageRepository;
import com.spring.onistore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.spring.onistore.util.Slugify.toSlug;

@RestController
@RequestMapping("api/products")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryProductRepository categoryProductRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    // Show all products
    @GetMapping("")
    @CrossOrigin("http://localhost:3006")
    public ResponseEntity<List<Product>> getAllProducts() {
        int count = productRepository.findAll().size();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Access-Control-Expose-Headers", "X-Total-Count");
        httpHeaders.add("X-Total-Count", String.valueOf(count));
        return ResponseEntity.ok().headers(httpHeaders).body(productRepository.findAll());
    }

    // Get products by product name
    @GetMapping("{product_slug}")
    public ResponseEntity<?> getProductBySlug(@PathVariable(value = "product_slug") String productSlug)
            throws ResourceNotFoundException {
        Optional<Product> product = productRepository.findBySlug(productSlug);
        if (product.isEmpty()) {
            Map<String, String> map = new HashMap<>();
            map.put("message", "Product not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        } else {
            return ResponseEntity.ok().body(product.get());
        }
    }

    // Create one product
    @PostMapping("")
    public Map<String, String> createProduct(@Validated @RequestBody ProductCateDto product) throws ResourceNotFoundException{
        Product product1 = new Product();
        product1.setSlug(toSlug(product.getName()));
        product1.setName(product.getName());
        product1.setDescription(product.getDescription());
        product1.setPrice(product.getPrice());
        product1.setFakePrice(product.getFakePrice());
        product1.setQuantity(product.getQuantity());
        Product saveProduct = productRepository.save(product1);
        if (product.getImage() != null) {
            Image image = new Image();
            image.setUrl(product.getImage());
            image.setProduct(saveProduct);
            imageRepository.save(image);
        }
        Category category = categoryRepository.findById(product.getCategoryID()).orElseThrow(() -> new ResourceNotFoundException("Cate not found"));
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProduct(saveProduct);
        productCategory.setCategory(category);
        ProductCategory productCategorySave = categoryProductRepository.save(productCategory);
        Map<String, String> map = new HashMap<>();
        map.put("message", "Add product successfully");
        return map;
    }

    // Delete one product
    @DeleteMapping("{id}")
    public Map<String, Boolean> deleteProduct(@PathVariable(value = "id") Long productId)
            throws ResourceNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product" + productId + "is not exist"));
        productRepository.delete(product);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    // Update product
    @PutMapping("{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Long productId,
            @Validated @RequestBody Product productDetail) throws ResourceNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product" + productId + "is not exist"));
        product.setName(productDetail.getName());
        product.setDescription(productDetail.getDescription());
        product.setSlug(toSlug(productDetail.getName()));
        final Product productUpdated = productRepository.save(product);
        return ResponseEntity.ok().body(productUpdated);
    }

}
