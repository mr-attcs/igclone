package com.mrattcs.instagramapi.controller;

import com.mrattcs.instagramapi.exceptions.CommentException;
import com.mrattcs.instagramapi.exceptions.PostException;
import com.mrattcs.instagramapi.exceptions.UserException;
import com.mrattcs.instagramapi.modal.Comment;
import com.mrattcs.instagramapi.modal.Post;
import com.mrattcs.instagramapi.modal.User;
import com.mrattcs.instagramapi.repo.PostRepository;
import com.mrattcs.instagramapi.service.CommentService;
import com.mrattcs.instagramapi.service.PostService;
import com.mrattcs.instagramapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping("/create/{postId}")
    public ResponseEntity<Comment> createCommentHandler(@RequestBody Comment comment, @PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException {
        User user = userService.findUserProfile(token);

        Comment createdComment = commentService.createComment(comment, postId, user.getId());

        return new ResponseEntity<>(createdComment, HttpStatus.OK);
    }

    @PutMapping("/like/{commentId}")
    public ResponseEntity<Comment> likeCommentHandler(@RequestHeader("Authorization") String token, @PathVariable Integer commentId) throws UserException, PostException, CommentException {
        User user = userService.findUserProfile(token);

        Comment comment = commentService.likeComment(commentId, user.getId());

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PutMapping("/unlike/{commentId}")
    public ResponseEntity<Comment> unlikeCommentHandler(@RequestHeader("Authorization") String token, @PathVariable Integer commentId) throws UserException, PostException, CommentException {
        User user = userService.findUserProfile(token);

        Comment comment = commentService.unlikeComment(commentId, user.getId());

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteCommentHandler(@PathVariable Integer commentId, @RequestHeader("Authorization") String token) throws PostException, CommentException, UserException {
        User user = userService.findUserProfile(token);
        String message = commentService.deleteComment(commentId, user.getId());
        return ResponseEntity.ok(message);

    }

}
