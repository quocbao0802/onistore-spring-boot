package com.spring.onistore.repository;

import com.spring.onistore.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryProductRepository extends JpaRepository<ProductCategory, Long> {

}
