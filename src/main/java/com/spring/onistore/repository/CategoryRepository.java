package com.spring.onistore.repository;

import com.spring.onistore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findBySlug(String Slug);


}