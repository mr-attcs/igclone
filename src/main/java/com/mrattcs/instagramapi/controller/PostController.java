package com.mrattcs.instagramapi.controller;

import com.mrattcs.instagramapi.exceptions.PostException;
import com.mrattcs.instagramapi.exceptions.UserException;
import com.mrattcs.instagramapi.modal.Post;
import com.mrattcs.instagramapi.modal.User;
import com.mrattcs.instagramapi.response.MessageResponse;
import com.mrattcs.instagramapi.service.PostService;
import com.mrattcs.instagramapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Post> createPostHandler(@RequestBody Post post, @RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        Post createdPost = postService.createPost(post, user.getId());

        return new ResponseEntity<>(createdPost, HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<Post>> findPostsByUserIdHandler(@PathVariable("id") Integer userId) throws UserException {
        List<Post> posts = postService.findPostByUserId(userId);

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/following/{ids}")
    public ResponseEntity<List<Post>> findAllPostsByUserIdsHandler(@PathVariable("ids") List<Integer> userIds) throws UserException, PostException {
        List<Post> posts = postService.findAllPostByUserId(userIds);

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> findPostByIdHandler(@PathVariable Integer postId) throws PostException {
        Post post = postService.findPostById(postId);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<Post> likePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws PostException, UserException {

        User user = userService.findUserProfile(token);

        Post likedPost = postService.likePost(postId, user.getId());

        return new ResponseEntity<>(likedPost, HttpStatus.OK);
    }

    @PutMapping("/unlike/{postId}")
    public ResponseEntity<Post> unlikePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws PostException, UserException {

        User user = userService.findUserProfile(token);

        Post unlikedPost = postService.unlikePost(postId, user.getId());

        return new ResponseEntity<>(unlikedPost, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException {
        User user = userService.findUserProfile(token);

        String message = postService.deletePost(postId, user.getId());

        return ResponseEntity.ok(message);
    }

    @PutMapping("/save_post/{postId}")
    public ResponseEntity<String> savedPostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException {
        User user = userService.findUserProfile(token);

        String message = postService.savedPost(postId, user.getId());

        return ResponseEntity.ok(message);
    }

    @PutMapping("/unsave_post/{postId}")
    public ResponseEntity<String> unsavedPostHandler(@PathVariable Integer postId, @RequestHeader("Authorization") String token) throws UserException, PostException {
        User user = userService.findUserProfile(token);

        String message = postService.unsavedPost(postId, user.getId());

        return ResponseEntity.ok(message);
    }

}
