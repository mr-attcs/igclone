package com.mrattcs.instagramapi.service;

import com.mrattcs.instagramapi.exceptions.CommentException;
import com.mrattcs.instagramapi.exceptions.PostException;
import com.mrattcs.instagramapi.exceptions.StoryException;
import com.mrattcs.instagramapi.exceptions.UserException;
import com.mrattcs.instagramapi.modal.Comment;
import com.mrattcs.instagramapi.modal.Post;
import com.mrattcs.instagramapi.modal.Story;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {

    Comment createComment(Comment comment, Integer postId, Integer userId) throws UserException, PostException;

    Comment findCommentById(Integer commentId) throws CommentException;

    Comment likeComment(Integer commentId, Integer userId) throws CommentException, UserException;

    Comment unlikeComment(Integer commentId, Integer userId) throws CommentException, UserException;

    String deleteComment (Integer commentId, Integer userId) throws CommentException, UserException, PostException;


}
