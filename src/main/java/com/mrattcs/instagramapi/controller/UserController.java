package com.mrattcs.instagramapi.controller;


import com.mrattcs.instagramapi.exceptions.UserException;
import com.mrattcs.instagramapi.modal.User;
import com.mrattcs.instagramapi.response.MessageResponse;
import com.mrattcs.instagramapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> findUserByIdHandler(@PathVariable Integer id) throws UserException {
        User user = userService.findUserById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> findUserByUsernameHandler(@PathVariable String username) throws UserException {
        User user = userService.findUserByUsername(username);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/follow/{followUserId}")
    public ResponseEntity<?> followUserHandler(@PathVariable Integer followUserId, @RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);

        String message = userService.followUser(user.getId(), followUserId);
        return ResponseEntity.ok(userService.followUser(user.getId(), followUserId));


    }

    @PutMapping("/unfollow/{userId}")
    public ResponseEntity<?> unfollowUserHandler(@PathVariable Integer userId, @RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        return ResponseEntity.ok(userService.unFollowUser(user.getId(), userId));

    }

    @GetMapping("/req")
    public ResponseEntity<User> findUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException {
        return new ResponseEntity<>(userService.findUserProfile(token), HttpStatus.OK);
    }

    @GetMapping("/m/{userIds}")
    public ResponseEntity<List<User>> findUsersByUserIdsHandler(@PathVariable List<Integer> userIds) throws UserException {
        List<User> users = userService.findUserByIds(userIds);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

//  api/users/search/?="query"
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUserHandler(@RequestParam("q") String query) throws UserException {
        List<User> users = userService.searchUser(query);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/account/edit")
    public ResponseEntity<User> updateUserHandler(@RequestHeader("Authorization") String token, @RequestBody User user) throws UserException {
        User existingUser = userService.findUserProfile(token);
        User updatedUser = userService.updateUserDetails(user, existingUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

}
