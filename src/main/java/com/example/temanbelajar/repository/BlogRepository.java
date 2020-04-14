package com.example.temanbelajar.repository;

import com.example.temanbelajar.model.Blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * BlogRepository
 */
@Transactional(readOnly = true)
public interface BlogRepository extends JpaRepository<Blog, Long>{

    @Query("select e from #{#entityName} e where e.title like %:param% OR "
    + "e.content like %:param%")
    Page<Blog> findByNameParams(Pageable pageable, String param);

    
    @Query(value = "select * from blog where blog.author_id = :author_id ", nativeQuery = true)
    Page<Blog> findByAuthor(Pageable pageable, @Param("author_id") Long author_id);

    @Query(value = "select * from blog where blog.categories_id = :categories_id ", nativeQuery = true)
    Page<Blog> findByCategory(Pageable pageable, @Param("categories_id") Long categories_id);
    
    @Query(value = "select blog.* from blog join blog_tags on blog.id = blog_tags.blog_id "
    +"join tags on tags.id = blog_tags.tags_id "
    +"where tags.name = :tag_name ", nativeQuery = true)
	Page<Blog> findByTag(Pageable pageable, @Param("tag_name") String tag_name);
    
}