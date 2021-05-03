package com.spring.onistore.repository;

import java.util.Optional;

import com.spring.onistore.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<Product> findBySlug(String Slug);
}
