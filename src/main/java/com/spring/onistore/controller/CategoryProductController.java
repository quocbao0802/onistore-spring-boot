package com.spring.onistore.controller;

import com.spring.onistore.dto.CategoryDto;
import com.spring.onistore.entity.Category;
import com.spring.onistore.entity.Product;
import com.spring.onistore.entity.ProductCategory;
import com.spring.onistore.repository.CategoryProductRepository;
import com.spring.onistore.repository.CategoryRepository;
import com.spring.onistore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/categories/products")
public class CategoryProductController {

    @Autowired
    private CategoryProductRepository categoryProductRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    public Map<String, List<?>> addCategoryProduct() {
        List<Category> category = categoryRepository.findAll();
        List<Product> products = productRepository.findAll();
        Map<String, List<?>> map = new HashMap<>();
        map.put("categories", category);
        map.put("products", products);
        return map;
    }

    @PostMapping("")
    public ResponseEntity<?> saveCategoryProduct(@RequestBody ProductCategory productCategory) {

//        Product product = productCategory.getProduct();
//        Category category =  productCategory.getCategory();
//
//        if(product == null || category == null){
//            return ResponseEntity.unprocessableEntity().build();
//        }
//


        ProductCategory saveProductCategory = categoryProductRepository.save(productCategory);
        return ResponseEntity.ok().body(saveProductCategory);
    }
}
