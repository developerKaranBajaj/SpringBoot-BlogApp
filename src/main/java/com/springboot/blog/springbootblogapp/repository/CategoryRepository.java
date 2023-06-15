package com.springboot.blog.springbootblogapp.repository;

import com.springboot.blog.springbootblogapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
