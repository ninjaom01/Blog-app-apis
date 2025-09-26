package com.govind.blogging.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.govind.blogging.payload.ApiResponse;
import com.govind.blogging.payload.CommentDto;
import com.govind.blogging.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CommentService commentService;

//Create comment
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable Integer postId) {

		CommentDto createComment = this.commentService.createComment(comment, postId);
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
	}

//delete comment	

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<CommentDto> deleteComment(@PathVariable Integer commentId) {

		this.commentService.deleteComment(commentId);
		return new ResponseEntity(new ApiResponse("Comment deleted Successfully !!!",true), HttpStatus.OK);
	}
}
