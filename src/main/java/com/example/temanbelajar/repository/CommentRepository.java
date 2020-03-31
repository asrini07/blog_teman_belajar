package com.example.temanbelajar.repository;

import com.example.temanbelajar.model.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * CommentRepository
 */
public interface CommentRepository extends JpaRepository<Comment, Long>{

    @Query(value = "SELECT * from Comment WHERE blog_id = ?1", nativeQuery = true)
    Comment findCommentBlog(Long blog_id);

    @Query(value = "SELECT comment from Comment comment WHERE comment.id = ?1 and comment.blog_id = ?2", nativeQuery = true)
    Comment findCommentId(Long id, Long blog_id);
}