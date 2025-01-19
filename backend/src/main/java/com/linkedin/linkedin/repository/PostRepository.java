package com.linkedin.linkedin.repository;

import com.linkedin.linkedin.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserIdNotOrderByCreatedAtDesc(Long userId);
    List<Post> findALlByOrderByCreatedAtDesc();
}
