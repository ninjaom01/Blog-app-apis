package com.govind.blogging.services;

import java.util.List;

//import com.govind.blogging.entities.Category;
import com.govind.blogging.payload.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	void deleteCategory(Integer categoryId);

	CategoryDto getCategory(Integer categoryId);

	List<CategoryDto> getcategories();
	
}
