package com.spring.onistore.controller;

import com.spring.onistore.entity.Category;
import com.spring.onistore.exception.ResourceNotFoundException;
import com.spring.onistore.repository.CategoryProductRepository;
import com.spring.onistore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.spring.onistore.util.Slugify.toSlug;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories() {
        int count = categoryRepository.findAll().size();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Access-Control-Expose-Headers", "X-Total-Count");
        httpHeaders.add("X-Total-Count", String.valueOf(count));
        return ResponseEntity.ok().headers(httpHeaders).body(categoryRepository.findAll());
    }

    // Get products by category slug
    @GetMapping("{category_slug}")
    public ResponseEntity<Category> getCategoryBySlug(@PathVariable(value = "category_slug") String categorySlug)
            throws ResourceNotFoundException {
        Category categories = categoryRepository.findBySlug(categorySlug);
        return ResponseEntity.ok().body(categories);
    }

    @PostMapping("")
    public ResponseEntity<Category> addCategory(@RequestBody Category Category) {
        Category.setSlug(toSlug(Category.getName()));
        Category saveCategory = categoryRepository.save(Category);
        return ResponseEntity.ok().body(saveCategory);
    }

    @PutMapping("{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable(value = "id") Long categoryId,
                                                   @RequestBody Category categoryDetail) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category " + categoryId + " is not exist"));
        category.setName(categoryDetail.getName());
        category.setDescription(categoryDetail.getDescription());
        category.setSlug(toSlug(categoryDetail.getName()));
        final Category categoryUpdated = categoryRepository.save(category);
        return ResponseEntity.ok().body(categoryUpdated);
    }

    @DeleteMapping("{id}")
    public Map<String, Boolean> deleteCategory(@PathVariable(value = "id") Long categoryId)
            throws ResourceNotFoundException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category " + categoryId + " is not exist"));
        categoryRepository.delete(category);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}