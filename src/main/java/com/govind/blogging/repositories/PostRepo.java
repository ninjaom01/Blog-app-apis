package com.govind.blogging.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.govind.blogging.entities.Category;
import com.govind.blogging.entities.Post;

public interface PostRepo extends JpaRepository<Post, Integer>{
	
	Page<Post> findByCategory(Category category, Pageable pageable);

	Page<Post> findByUserId(Integer userId, Pageable pageable);

	List<Post> findByTitleContaining(String keyword);
	
}