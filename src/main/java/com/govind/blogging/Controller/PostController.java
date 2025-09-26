package com.govind.blogging.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.govind.blogging.config.AppConstants;
import com.govind.blogging.payload.ApiResponse;
import com.govind.blogging.payload.PostDto;
import com.govind.blogging.payload.PostResponse;
import com.govind.blogging.services.FileService;
import com.govind.blogging.services.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;
    
    @Autowired
    private FileService fileService;
    
    @Value("${project.image}")
    private String path;

    
    //Post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId) {

        PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
    
    //delete post
    @DeleteMapping("/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId) {
    	this.postService.deletePost(postId);
    	return new ApiResponse("Post is Deleted", true);
    }
    
    //Update post
    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
    	
    	PostDto updatePost = this.postService.updatePost(postDto, postId);
    	return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
    }
        
    
    
    //Get by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostsByUser(
            @PathVariable Integer userId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {

        PostResponse response = this.postService.getPostByUser(userId, pageNumber, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
    //get by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostsByCategory(
            @PathVariable Integer categoryId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {

        PostResponse response = this.postService.getPostByCategory(categoryId, pageNumber, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

   
    //get post by id
    @GetMapping("/{postId}")
    public  ResponseEntity<PostDto> getPostById(@PathVariable Integer postId)
    {
    	PostDto postDto=this.postService.getPostById(postId);
    	return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
    }
    
    
    //get all post
    @GetMapping("/")
    public ResponseEntity<PostResponse> getAllPost(
    		@RequestParam(value = "pageNumber",defaultValue =AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
    		@RequestParam(value = "pageSize",defaultValue =AppConstants.PAGE_SIZE,required = false)Integer pageSize,
    		@RequestParam(value = "sortBy",defaultValue =AppConstants.SORT_BY,required = false)String sortBy,
    		@RequestParam(value = "sortDir",defaultValue =AppConstants.SORT_DIR,required = false)String sortDir
    		) {
    	 PostResponse  postResponse=(PostResponse) this.postService.getAllPost(pageNumber , pageSize, sortBy,sortDir);
    	return new ResponseEntity< PostResponse>(postResponse,HttpStatus.OK);  	
    }
    
  
    //searching
    @GetMapping("/search/{Keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(
    		@PathVariable("Keywords")String keywords){
    	List<PostDto> result= this.postService.searchByPosts(keywords);
				return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
    	
    }

    
    
    //post image upload
    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
    		@PathVariable Integer postId) throws IOException{
    	
    	 PostDto postDto = this.postService.getPostById(postId);
         String fileName =	this.fileService.uploadImage(path, image);
         postDto.setImageName(fileName);
         PostDto updatePost = this.postService.updatePost(postDto, postId);
         
		 return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);	
    }
    

    
}
    

 

