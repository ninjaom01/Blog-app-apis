package com.govind.blogging.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.govind.blogging.entities.Comment;

public interface CommentRepo extends JpaRepository <Comment, Integer> {

}
