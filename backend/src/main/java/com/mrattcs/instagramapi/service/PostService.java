package com.mrattcs.instagramapi.service;

import com.mrattcs.instagramapi.exceptions.PostException;
import com.mrattcs.instagramapi.exceptions.UserException;
import com.mrattcs.instagramapi.modal.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    Post createPost(Post post, Integer userId) throws UserException;

    String deletePost(Integer postId, Integer userId) throws UserException, PostException;

    List<Post> findPostByUserId(Integer userId) throws UserException;

    Post findPostById(Integer postId) throws PostException;

    List<Post> findAllPostByUserId(List<Integer> userIds) throws PostException, UserException;

    String savedPost(Integer postId, Integer userId) throws PostException, UserException;

    String unsavedPost(Integer postId, Integer userId) throws PostException, UserException;

    Post likePost(Integer postId, Integer userId) throws PostException, UserException;

    Post unlikePost(Integer postId, Integer userId) throws PostException, UserException;

    Post editPost(Post updatedPost, Post existingPost) throws PostException;



}
