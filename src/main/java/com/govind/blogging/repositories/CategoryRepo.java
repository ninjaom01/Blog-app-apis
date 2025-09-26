package com.govind.blogging.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.govind.blogging.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{
	
	

}
