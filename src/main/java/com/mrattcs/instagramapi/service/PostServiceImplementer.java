package com.mrattcs.instagramapi.service;

import com.mrattcs.instagramapi.dto.UserDto;
import com.mrattcs.instagramapi.exceptions.PostException;
import com.mrattcs.instagramapi.exceptions.UserException;
import com.mrattcs.instagramapi.modal.Post;
import com.mrattcs.instagramapi.modal.User;
import com.mrattcs.instagramapi.repo.CommentRepository;
import com.mrattcs.instagramapi.repo.PostRepository;
import com.mrattcs.instagramapi.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImplementer implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;

    public PostServiceImplementer(UserService userService, CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.userService = userService;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Post createPost(Post post, Integer userId) throws UserException {
        User user = userService.findUserById(userId);

        UserDto uDto = new UserDto();
        uDto.setEmail(user.getEmail());
        uDto.setId(user.getId());
        uDto.setName(user.getName());
        uDto.setUserimage(user.getImage());
        uDto.setUsername(user.getUsername());

        post.setCreatedAt(LocalDateTime.now());
        post.setUser(uDto);

        return postRepository.save(post);
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws UserException, PostException {
        User user = userService.findUserById(userId);

        Post existingPost = findPostById(postId);

        if (existingPost.getUser().getId().equals(user.getId())) {
            postRepository.deleteById(postId);
            return "Post deleted successfully";
        }

        throw new PostException("You can't delete other user's post");
    }


    @Override
    public List<Post> findPostByUserId(Integer userId) throws UserException {

        List<Post> posts = postRepository.findByUserId(userId);

        if (posts.isEmpty()) {
            throw new UserException("This user does not have any posts");
        }
        return posts;
    }

    @Override
    public Post findPostById(Integer postId) throws PostException {

        Optional<Post> oPost = postRepository.findById(postId);

        if (oPost.isPresent()) {
            return oPost.get();
        }

        throw new PostException("Post with ID " + postId + " does not exist");
    }

    @Override
    public List<Post> findAllPostByUserId(List<Integer> userIds) throws PostException {

        List<Post> posts = postRepository.findAllPostByUserIds(userIds);

        if (posts.isEmpty()) {
            throw new PostException("No posts available");
        }

        return posts;
    }

    @Override
    public String savedPost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);

        User user = userService.findUserById(userId);

        if (!user.getSavedPost().contains(post)) {
            user.getSavedPost().add(post);
            userRepository.save(user);
            return "Post saved successfully";
        }
        throw new PostException("Post is already saved.");
    }

    @Override
    public String unsavedPost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);

        User user = userService.findUserById(userId);

        if (user.getSavedPost().contains(post)) {
            user.getSavedPost().remove(post);
            userRepository.save(user);
            return "Post removed successfully";
        }
        throw new PostException("Post not found");
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws PostException, UserException {

        Post post = findPostById(postId);

        User user = userService.findUserById(userId);

        UserDto uDto = new UserDto();
        uDto.setEmail(user.getEmail());
        uDto.setId(user.getId());
        uDto.setName(user.getName());
        uDto.setUserimage(user.getImage());
        uDto.setUsername(user.getUsername());

        post.getLikedByUsers().add(uDto);

        return postRepository.save(post);
    }

    @Override
    public Post unlikePost(Integer postId, Integer userId) throws PostException, UserException {
        Post post = findPostById(postId);

        User user = userService.findUserById(userId);

        UserDto uDto = new UserDto();
        uDto.setEmail(user.getEmail());
        uDto.setId(user.getId());
        uDto.setName(user.getName());
        uDto.setUserimage(uDto.getUserimage());
        uDto.setUsername(user.getUsername());

        var oLikeToRemove = post.getLikedByUsers().stream().filter(x -> x.getId().equals(uDto.getId())).findFirst();
        if (oLikeToRemove.isEmpty()) {
            throw new PostException("the like was not found and therefore could not be removed");
        }
        post.getLikedByUsers().remove(oLikeToRemove.get());

        return postRepository.save(post);
    }

    @Override
    public Post editPost(Post updatedPost, Post existingPost) throws PostException {
        if (updatedPost.getComments() != null) {
            existingPost.setComments(updatedPost.getComments());
        }
        if (updatedPost.getUser() != null) {
            existingPost.setUser(updatedPost.getUser());
        }
        if (updatedPost.getImage() != null) {
            existingPost.setImage(updatedPost.getImage());
        }
        if (updatedPost.getLikedByUsers() != null) {
            existingPost.setLikedByUsers(updatedPost.getLikedByUsers());
        }
        if (updatedPost.getCaption() != null) {
            existingPost.setCaption(updatedPost.getCaption());
        }
        if (updatedPost.getLocation() != null) {
            existingPost.setLocation(updatedPost.getLocation());
        }
        if (updatedPost.getCreatedAt() != null) {
            existingPost.setCreatedAt(updatedPost.getCreatedAt());
        }
        if (updatedPost.getId().equals(existingPost.getId())) {
            return postRepository.save(existingPost);
        }
        throw new PostException("You can't update this user");
    }

}
