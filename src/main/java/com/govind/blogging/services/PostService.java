package com.govind.blogging.services;

import java.util.List;

import com.govind.blogging.payload.PostDto;
import com.govind.blogging.payload.PostResponse;

public interface PostService {

	//create
	
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	//update post
	
	PostDto updatePost(PostDto postDto,Integer postId);
	
	
	//delete 
	void deletePost(Integer postId);
	
	
	//get all post
	PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
	
	
	//get one post
	PostDto getPostById(Integer postId);
	
	

	//get All post by user
	PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize);
	
	//get all post by Category
	PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);
	
	
	
	//Search Posts
	List<PostDto> searchByPosts(String keyword);





	


}
