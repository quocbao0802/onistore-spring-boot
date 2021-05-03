package com.spring.onistore.repository;

import com.spring.onistore.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRespository extends JpaRepository<Comment, Long> {
}
