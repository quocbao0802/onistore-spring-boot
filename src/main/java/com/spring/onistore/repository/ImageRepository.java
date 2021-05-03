package com.spring.onistore.repository;

import com.spring.onistore.entity.Image;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
