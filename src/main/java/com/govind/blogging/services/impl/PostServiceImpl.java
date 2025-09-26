package com.govind.blogging.services.impl;



import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.govind.blogging.entities.Category;
import com.govind.blogging.entities.Post;
import com.govind.blogging.entities.User;
import com.govind.blogging.exceptions.ResourceNotFoundException;
import com.govind.blogging.payload.PostDto;
import com.govind.blogging.payload.PostResponse;
import com.govind.blogging.repositories.CategoryRepo;
import com.govind.blogging.repositories.PostRepo;
import com.govind.blogging.repositories.UserRepo;
import com.govind.blogging.services.PostService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;
 
    
//create post    
    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setDate(new Date()); // should match the field name in entity
        post.setUser(user);
        post.setCategory(category);
        Post newPost = this.postRepo.save(post);

        return this.modelMapper.map(newPost, PostDto.class);
    }


//update post
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));
	    post.setTitle(postDto.getTitle());
	    post.setContent(postDto.getContent());
	    post.setImageName(postDto.getImageName());
	    
		Post UpdatedPost = this.postRepo.save(post);
		
		return this.modelMapper.map(UpdatedPost, PostDto.class);
	}

	
//delete post	
	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));
		this.postRepo.delete(post);
		
	}
	
	
//get all post	
	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
	 
		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

		
	    Pageable p = PageRequest.of(pageNumber, pageSize,sort);

	    Page<Post> pagePost = this.postRepo.findAll(p);
	    List<Post> allPosts = pagePost.getContent();

	    List<PostDto> postDtos = allPosts.stream()
	            .map(post -> this.modelMapper.map(post, PostDto.class))
	            .collect(Collectors.toList());
	    
	    PostResponse postResponse=new PostResponse();
	    
	    postResponse.setContent(postDtos);
	    postResponse.setPageNumber(pagePost.getNumber());
	    postResponse.setPageSize(pagePost.getSize());
	    postResponse.setTotalElements(pagePost.getTotalElements());
	    postResponse.setTotalPages(pagePost.getTotalPages());
	    postResponse.setLastPage(pagePost.isLast());
	    
	    return postResponse;
	}


	
	//get post by id
	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));
		return this.modelMapper.map(post ,PostDto.class);
	}
	
	
//get post by category
	@Override
	public PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {
	    Pageable pageable = PageRequest.of(pageNumber, pageSize);

	    Category category = categoryRepo.findById(categoryId)
	        .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

	    Page<Post> pagePosts = this.postRepo.findByCategory(category, pageable);
	    List<Post> posts = pagePosts.getContent();

	    List<PostDto> postDtos = posts.stream()
	            .map(post -> this.modelMapper.map(post, PostDto.class))
	            .collect(Collectors.toList());

	    PostResponse postResponse = new PostResponse();
	    postResponse.setContent(postDtos);
	    postResponse.setPageNumber(pagePosts.getNumber());
	    postResponse.setPageSize(pagePosts.getSize());
	    postResponse.setTotalElements(pagePosts.getTotalElements());
	    postResponse.setTotalPages(pagePosts.getTotalPages());
	    postResponse.setLastPage(pagePosts.isLast());

	    return postResponse;
	}


	
	
//Get post by user id
	@Override
	public PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize) {
	    Pageable pageable = PageRequest.of(pageNumber, pageSize);
	    Page<Post> pagePosts = this.postRepo.findByUserId(userId, pageable);
	    List<Post> posts = pagePosts.getContent();

	    List<PostDto> postDtos = posts.stream()
	            .map(post -> this.modelMapper.map(post, PostDto.class))
	            .collect(Collectors.toList());

	    PostResponse postResponse = new PostResponse();
	    postResponse.setContent(postDtos);
	    postResponse.setPageNumber(pagePosts.getNumber());
	    postResponse.setPageSize(pagePosts.getSize());
	    postResponse.setTotalElements(pagePosts.getTotalElements());
	    postResponse.setTotalPages(pagePosts.getTotalPages());
	    postResponse.setLastPage(pagePosts.isLast());

	    return postResponse;
	}


	//Search
	@Override
	public List<PostDto> searchByPosts(String keyword) {
    List<Post> posts = this.postRepo.findByTitleContaining(keyword);
	List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	
	return postDtos;
	}

}
