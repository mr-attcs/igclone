package com.mrattcs.instagramapi.service;

import com.mrattcs.instagramapi.dto.UserDto;
import com.mrattcs.instagramapi.exceptions.CommentException;
import com.mrattcs.instagramapi.exceptions.PostException;
import com.mrattcs.instagramapi.exceptions.UserException;
import com.mrattcs.instagramapi.modal.Comment;
import com.mrattcs.instagramapi.modal.Post;
import com.mrattcs.instagramapi.modal.User;
import com.mrattcs.instagramapi.repo.CommentRepository;
import com.mrattcs.instagramapi.repo.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImplementer implements CommentService {

    private final UserService userService;
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final PostRepository postRepository;

    public CommentServiceImplementer(UserService userService, CommentRepository commentRepository, PostService postService, PostRepository postRepository) {
        this.userService = userService;
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @Override
    public Comment createComment(Comment comment, Integer postId, Integer userId) throws UserException, PostException {
        User user = userService.findUserById(userId);
        Post post = postService.findPostById(postId);

        UserDto uDto = new UserDto();
        uDto.setEmail(user.getEmail());
        uDto.setId(user.getId());
        uDto.setName(user.getName());
        uDto.setUserimage(user.getImage());
        uDto.setUsername(user.getUsername());

        comment.setUser(uDto);
        comment.setCreatedAt(LocalDateTime.now());

        Comment createdComment = commentRepository.save(comment);

        post.getComments().add(createdComment);

        postRepository.save(post);

        return createdComment;
    }

    @Override
    public Comment findCommentById(Integer commentId) throws CommentException {
        Optional<Comment> oComment = commentRepository.findById(commentId);
        if (oComment.isPresent()) {
            return oComment.get();
        }
        throw new CommentException("Comment with ID: " + commentId + " is not existing");
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws CommentException, UserException {
        User user = userService.findUserById(userId);
        Comment comment = findCommentById(commentId);

        UserDto uDto = new UserDto();
        uDto.setEmail(user.getEmail());
        uDto.setId(user.getId());
        uDto.setName(user.getName());
        uDto.setUserimage(user.getImage());
        uDto.setUsername(user.getUsername());

        comment.getLikedByUsers().add(uDto);

        return commentRepository.save(comment);
    }

    @Override
    public Comment unlikeComment(Integer commentId, Integer userId) throws CommentException, UserException {
        User user = userService.findUserById(userId);
        Comment comment = findCommentById(commentId);

        UserDto uDto = new UserDto();
        uDto.setEmail(user.getEmail());
        uDto.setId(user.getId());
        uDto.setName(user.getName());
        uDto.setUserimage(user.getImage());
        uDto.setUsername(user.getUsername());

        comment.getLikedByUsers().remove(uDto);

        return commentRepository.save(comment);
    }

    @Override
    public String deleteComment(Integer commentId, Integer userId) throws CommentException, UserException, PostException {
        Comment comment = findCommentById(commentId);

        User user = userService.findUserById(userId);
        Post existingPost = postRepository.findPostsByCommentId(commentId);
        Post updatedPost = existingPost;
        if (comment.getUser().getId().equals(user.getId())) {
            if (!updatedPost.getLikedByUsers().isEmpty()) {
                for (UserDto u : updatedPost.getLikedByUsers()) {
                    updatedPost.getLikedByUsers().remove(u);
                }
            }
            updatedPost.getComments().remove(comment);
            postService.editPost(updatedPost, existingPost);
            commentRepository.deleteById(commentId);
            return "Comment deleted successfully";
        }
        throw new CommentException("You can't delete other user's comments.");
    }


}
