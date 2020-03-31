package com.example.temanbelajar.repository;

import java.util.ArrayList;
import java.util.List;

import com.example.temanbelajar.dto.response.ReponseCommentDto;
import com.example.temanbelajar.model.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * CommentRepository
 */
public interface CommentRepository extends JpaRepository<Comment, Long>{

    @Query(value = "SELECT * from Comment WHERE blog_id = ?1", nativeQuery = true)
    Comment findCommentBlog(Long blog_id);

    // Page<Comment> findByBlogId(Long postId, Pageable pageable);

    
    List<String> list = new ArrayList<>();
    ReponseCommentDto findByBlogId(Long postId);


    
    // @Query(value = "SELECT * from Comment WHERE blog_id = ?1", nativeQuery = true)
    // Comment findByBlogId(Long blog_id);

    //Optional<Comment> findByIdAndPostId(Long id;

    // List<Long> tagtag = blogData.getTags_id();
    //     ArrayList<Tags> tags = new ArrayList<Tags>();

    @Query(value = "SELECT comment from Comment comment WHERE comment.id = ?1 and comment.blog_id = ?2", nativeQuery = true)
    Comment findCommentId(Long id, Long blog_id);
}