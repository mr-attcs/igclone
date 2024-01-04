package com.mrattcs.instagramapi.repo;

import com.mrattcs.instagramapi.modal.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "select * from posts p where p.user_id=?1", nativeQuery = true)
    List<Post> findByUserId(Integer userId);

    @Query(value = "select p.* from posts p where p.user_id IN :users ORDER BY p.created_At DESC", nativeQuery = true)
    List<Post> findAllPostByUserIds(@Param("users") List<Integer> userIds);

    @Query(value = "select * from posts p join posts_comments pc ON p.id = pc.post_id WHERE pc.id = ?1", nativeQuery = true)
    Post findPostsByCommentId(Integer commentId);

}