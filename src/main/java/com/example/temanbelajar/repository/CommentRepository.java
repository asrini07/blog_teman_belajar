package com.example.temanbelajar.repository;

import java.util.List;

import com.example.temanbelajar.model.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * CommentRepository
 */
@Transactional(readOnly = true)
public interface CommentRepository extends JpaRepository<Comment, Long>{

    @Query(value = "SELECT * from Comment WHERE blog_id = ?1", nativeQuery = true)
    List<Comment> findCommentBlog(Long blogId);

    @Query(value = "SELECT * from Comment WHERE blog_id = ?1", nativeQuery = true)
    Page<Comment> findCommentBlogPagination(Long blogId, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Comment WHERE comment.id = ?1 AND comment.blog_id = ?2", nativeQuery = true)
    void deleteCommentId(Long id, Long blogId);

    Comment findByIdAndBlogId(Long id, Long blogId);
    
    @Query(value = "SELECT * from comment WHERE blog_id = ?1 AND guest_email like %?2%" ,nativeQuery = true )
    Page<Comment> findByNameParamsBlog(Long blogId, Pageable pageable, String param);


}